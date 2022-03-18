package park.ilwoo.javis.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import park.ilwoo.javis.common.AESEncryption;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.common.JwtTokenProvider;
import park.ilwoo.javis.common.UserAuthentication;
import park.ilwoo.javis.user.entity.Login;
import park.ilwoo.javis.user.entity.User;
import park.ilwoo.javis.user.repository.UserRepository;

import java.util.Optional;

/**
 * User Service
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인
     * @param loginInfo ID, PWD담은 객체
     * @return String -> jwt Token
     * @throws CustomException
     */
    public String loginUser(Login loginInfo) throws Exception {
        Optional<User> findUserOptional = findUserByUserId(loginInfo.getUserId());

        //  1. Id가 없을시 throw
        User findUser = findUserOptional.orElseThrow(() -> new CustomException("201", "없는 ID 입니다."));

        //  2. 입력받은 패스워드 값과 DB에 저장된 값 비교
        if(!AESEncryption.instance.isEqual(loginInfo.getPassword(), findUser.getPassword())) throw new CustomException("202", "비밀번호가 맞지 않습니다.");
//        if(!passwordEncoder.matches(loginInfo.getPassword(), findUser.getPassword()))

        Authentication authentication = new UserAuthentication(findUser.getUserId(), null);
        String token = JwtTokenProvider.generateToken(authentication);

        return token;
    }

    /**
     * 회원가입
     * @param user 아이디,비밀번호,이름,주민번호를 담은 객체
     */
    public void joinUser(User user) throws Exception {

        //  주민번호 암호화
        String encodeRegNo = AESEncryption.instance.encrypt(user.getRegNo());

        Optional<User> findRegNoOptional = userRepository.findByRegNo(encodeRegNo);
        //  이미 가입한 회원일경우
        if(findRegNoOptional.isPresent()){
            throw new CustomException("103", "이미 가입한 회원입니다.");
        }

        Optional<User> findUserOptional = findUserByUserId(user.getUserId());
        //  UserId가 이미 존재할경우
        if(findUserOptional.isPresent()){
//            throw new DuplicateKeyException("DuplicateUserID");
            throw new CustomException("103", "이미 존재하는 ID입니다.");
        }

        user.setRegNo(encodeRegNo);
        //  Password 암호화
        String encodePwd = AESEncryption.instance.encrypt(user.getPassword());
        user.setPassword(encodePwd);

        userRepository.save(user);
    }

    /**
     * userId로 User객체 찾기
     * @param userId 입력받은 UserId
     * @return Optional User 객체
     */
    public Optional<User> findUserByUserId(String userId){
        Optional<User> findUser = userRepository.findByUserId(userId);
        return findUser;
    }

    /**
     * 복호화한 User 정보
     * @param me
     * @return
     * @throws Exception
     */
    public User getDecryptInfo(User me) throws Exception {
        //  Password 복호화
        String decodePwd = AESEncryption.instance.decrypt(me.getPassword());
        me.setPassword(decodePwd);
        //  주민번호 복호화
        String decodeRegNo = AESEncryption.instance.decrypt(me.getRegNo());
        me.setRegNo(decodeRegNo);

        return me;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUserOptional = findUserByUserId(username);
        User findUser = findUserOptional.orElseThrow(() -> new UsernameNotFoundException("Not Found ID"));
        return findUser;
    }
}

