package park.ilwoo.javis.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request Body 바꿀 수 없어서 새로 만들었습니다..
 */
@Data
@ApiModel(value = "로그인정보", description = "아이디, 비밀번호 갖는 DTO")
public class Login {

    @ApiModelProperty(name = "유저PWD",  example = "123456")
    private String password;
    @ApiModelProperty(name = "유저ID",  example = "loveelf1")
    private String userId;
}
