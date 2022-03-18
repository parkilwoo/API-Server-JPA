package park.ilwoo.javis.user;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.common.Result;
import park.ilwoo.javis.common.Utils;
import park.ilwoo.javis.user.entity.Login;
import park.ilwoo.javis.user.entity.User;
import park.ilwoo.javis.valid.UserJoinValidator;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "USER", description = "로그인&회원가입")
@Slf4j
public class UserRestController {

    //  허용된 주민번호만 Matching을 위한 Validator
    private final UserJoinValidator userJoinValidator;
    private final UserService userService;

    public UserRestController(UserJoinValidator userJoinValidator, UserService userService) {
        this.userJoinValidator = userJoinValidator;
        this.userService = userService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 응답값"),
            @ApiResponse(responseCode = "404", description = "페이지 없음"),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    @Tag(name = "USER", description = "회원가입")
    @ApiOperation(
            value = "회원가입"
            , notes = "유저 회원가입"
            , produces = "application/json"
            , response = Result.class
    )
    @PostMapping("/szs/signup")
    public Result singUp(@RequestBody @Validated @ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) User user, Errors errors) throws Exception {
        Result result = new Result();
        //  기본 Validate
        if(errors.hasErrors()) {
            result.setFail("101", Utils.getValidErrorMessage(errors));
            return result;
        }
        //  허용된 주민번호,이름만 Validate
        userJoinValidator.validate(user,errors);
        if(errors.hasErrors()) {
            result.setFail("102", Utils.getValidErrorMessage(errors));
            return result;
        }
        //  Join User
        userService.joinUser(user);
        result.setSuccess();
        return result;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 응답값"),
            @ApiResponse(responseCode = "404", description = "페이지 없음"),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    @Tag(name = "USER", description = "로그인")
    @ApiOperation(
            value = "로그인"
            , notes = "유저 로그인"
            , produces = "application/json"
            , response = Result.class
    )
    @PostMapping("/szs/login")
    public Result login(@RequestBody Login loginUser) throws Exception {
        Result result = new Result();

        String token = userService.loginUser(loginUser);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        result.setSuccess(tokenMap);
        return result;
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 응답값"),
            @ApiResponse(responseCode = "404", description = "페이지 없음"),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    @Tag(name = "USER", description = "나의정보")
    @ApiOperation(
            value = "나의정보"
            , notes = "로그인한 유저 정보"
            , produces = "application/json"
            , response = Result.class
    )
    @Tag(name = "USER", description = "나의정보")
    @GetMapping("/szs/me")
    public Result infoMe(@AuthenticationPrincipal @ApiIgnore User me) throws Exception {
        Result result = new Result();
        me = userService.getDecryptInfo(me);
        result.setSuccess(me);

        return result;
    }
}
