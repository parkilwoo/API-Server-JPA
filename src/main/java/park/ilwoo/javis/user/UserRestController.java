package park.ilwoo.javis.user;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import park.ilwoo.javis.common.CustomException;
import park.ilwoo.javis.common.Result;
import park.ilwoo.javis.common.Utils;
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
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Tag(name = "USER", description = "회원가입")
    @PostMapping("/szs/signup")
    public Result singUp(@RequestBody @Validated User user, Errors errors) {
        Result result = new Result();
        //  기본 Validate
        if(errors.hasErrors()) {
            result.setFail("500", Utils.getValidErrorMessage(errors));
            return result;
        }
        //  허용된 주민번호,이름만 Validate
        userJoinValidator.validate(user,errors);
        if(errors.hasErrors()) {
            result.setFail("501", Utils.getValidErrorMessage(errors));
            return result;
        }
        //  Join User
        try{
            userService.joinUser(user);
            result.setSuccess();
        }
        catch (CustomException ce) {
            result.setFail(ce.getERR_CODE(), ce.getERR_MSG());
        }
        catch (Exception e) {
            result.setFail();
        }
        return result;
    }

    @Tag(name = "USER", description = "로그인")
    @PostMapping("/szs/login")
    public Result login(@RequestBody User loginUser) {
        Result result = new Result();

        try {
            String token = userService.loginUser(loginUser);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            result.setSuccess(tokenMap);
        }
        catch (CustomException ce) {
            result.setFail(ce.getERR_CODE(), ce.getERR_MSG());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            result.setFail();
        }
        return result;
    }


    @Tag(name = "USER", description = "나의정보")
    @PostMapping("/szs/me")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    public Result infoMe(@AuthenticationPrincipal @ApiIgnore User me) {
        Result result = new Result();
        result.setData(me);
        return result;
    }
}
