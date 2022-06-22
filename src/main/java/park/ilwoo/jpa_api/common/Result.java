package park.ilwoo.jpa_api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

/**
 * API Result를 담당하는 Class
 */
@Data
@ApiModel(value = "Response 응답객체", description = "공통 Response 객체")
public class Result {
    // Getter 실행 X
    @Getter(AccessLevel.NONE)
    private MessageSource messageSource = (MessageSource) Utils.getBean("messageSource");

    @ApiModelProperty(name = "응답코드", value = "000:정상, 999:통신실패, 그 외:Exception", example = "000")
    private String code;          // result code
    @ApiModelProperty(name = "응답메세지", value = "성공, 실패, 그 외", example = "성공")
    private String msg;           // result message
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "응답데이터", value = "JSON 응답", example = "{name: '박일우'}")
    private Object data;          // result data

    /**
     * Resp Success 세팅(Response Data 있을시)
     *
     * @param data data
     */
    public void setSuccess(Object data) {
        this.msg = messageSource.getMessage("result.success.msg", null, Locale.getDefault());
        this.code = messageSource.getMessage("result.success.code", null, Locale.getDefault());
        if (!Objects.equals(data, null)) this.data = data;
    }

    /**
     * Default Resp Success
     */
    public void setSuccess() {
        this.msg = messageSource.getMessage("result.success.msg", null, Locale.getDefault());
        this.code = messageSource.getMessage("result.success.code", null, Locale.getDefault());
    }


    /**
     * Default Resp Fail(exception)
     */
    public void setFail() {
        this.msg = messageSource.getMessage("result.fail.msg", null, Locale.getDefault());
        this.code = messageSource.getMessage("result.fail.code", null, Locale.getDefault());
    }

    /**
     * Resp Fail(exception)
     */
    public void setFail(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
