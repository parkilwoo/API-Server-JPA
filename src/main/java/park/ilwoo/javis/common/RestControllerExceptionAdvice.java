package park.ilwoo.javis.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
/**
 * RestController Exception Advice
 */
public class RestControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public Result unauthorizedUserExceptionHandler(CustomException exception) {
        Result result = new Result();
        result.setFail(exception.getERR_CODE(), exception.getERR_MSG());
        return result;
    }


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result unauthorizedUserExceptionHandler(Exception exception) {
        log.error(exception.getMessage());
        Result result = new Result();
        result.setFail();
        return result;
    }
}
