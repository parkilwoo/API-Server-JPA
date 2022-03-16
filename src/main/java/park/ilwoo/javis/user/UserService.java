package park.ilwoo.javis.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.common.JwtTokenProvider;
import park.ilwoo.javis.common.UserAuthentication;
import park.ilwoo.javis.user.entity.User;
import park.ilwoo.javis.user.repository.UserRepository;

import java.util.Optional;

/**
 * User Service
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인
     * @param loginInfo ID, PWD담은 객체
     * @return String -> jwt Token
     * @throws CustomException
     */
    public String loginUser(User loginInfo) throws CustomException {
        Optional<User> findUserOptional = findUserByUserId(loginInfo.getUserId());

        //  1. Id가 없을시 throw
//        User findUser = findUserOptional.orElseThrow(() -> new UsernameNotFoundException("Not Found UserID"));
        User findUser = findUserOptional.orElseThrow(() -> new CustomException("901", "없는 ID 입니다."));

        //  2. 입력받은 패스워드 Encode값이 DB에 저장된 값 비교
        if(!passwordEncoder.matches(loginInfo.getPassword(), findUser.getPassword())) throw new CustomException("902", "비밀번호가 맞지 않습니다.");

        Authentication authentication = new UserAuthentication(findUser.getUserId(), null);
        String token = JwtTokenProvider.generateToken(authentication);

        return token;
    }

    /**
     * 회원가입
     * @param user 아이디,비밀번호,이름,주민번호를 담은 객체
     */
    public void joinUser(User user) throws CustomException {
        Optional<User> findUserOptional = findUserByUserId(user.getUserId());

        //  UserId가 이미 존재할경우
        if(findUserOptional.isPresent()){
//            throw new DuplicateKeyException("DuplicateUserID");
            throw new CustomException("900", "이미 존재하는 ID입니다.");
        }
        //  Password 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUserOptional = findUserByUserId(username);
        User findUser = findUserOptional.orElseThrow(() -> new UsernameNotFoundException("Not Found ID"));
        return findUser;
    }
}

