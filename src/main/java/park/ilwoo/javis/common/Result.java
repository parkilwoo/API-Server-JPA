package park.ilwoo.javis.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

/**
 * API Result를 담당하는 Class
 */
@Data
public class Result {
    // Getter 실행 X
    @Getter(AccessLevel.NONE)
    private MessageSource messageSource = (MessageSource) Utils.getBean("messageSource");

    private String code;          // result code
    private String msg;           // result message
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
