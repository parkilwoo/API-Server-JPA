package park.ilwoo.javis.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Custom Exception 만들기 위한 Class
 */
public class CustomException extends Exception {
    private String ERR_CODE;
    private String ERR_MSG;
    private Object data = null;

    public CustomException(String err_code, String err_msg){
        ERR_CODE = err_code;
        ERR_MSG = err_msg;
    }

    public CustomException(String err_code, String err_msg, Object data){
        ERR_CODE = err_code;
        ERR_MSG = err_msg;
        this.data = data;
    }
}
