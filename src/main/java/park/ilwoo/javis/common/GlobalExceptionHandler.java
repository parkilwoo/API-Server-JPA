package park.ilwoo.javis.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.rmi.UnexpectedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * spring data jpa valid 실패 시 handler
     *
     * @param exception ConstraintViolationException
     * @return result 객체
     */
    @ExceptionHandler(value = ConstraintViolationException.class) // 유효성 검사 실패 시 발생하는 예외를 처리
    @ResponseBody
    protected Result handleException(ConstraintViolationException exception) {
        log.error("handleException() called with: exception = [" + exception + "]");
        Result result = new Result();
        result.setMsg(exception.getMessage());
        return result;
    }

    @ExceptionHandler(value = UnexpectedException.class)
    @ResponseBody
    protected Result handleException(UnexpectedException exception) {
        log.error("handleException() called with: exception = [" + exception + "]");
        Result result = new Result();
        result.setFail();
        result.setMsg(exception.getMessage());
        return result;
    }
}
