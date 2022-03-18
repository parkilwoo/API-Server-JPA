package park.ilwoo.javis.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * Util Class
 */
@Slf4j
public class Utils {

    /**
     * ApplicationContext에서 Bean객체를 얻는 Method
     *
     * @param beanName 얻으려는 Bean객체 이름
     * @return bean object
     */
    public static Object getBean(String beanName) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(beanName);
    }

    /**
     * Valid Error 메세지 출력 Utils Method
     * @param errors
     * @return
     */
    public static String getValidErrorMessage(Errors errors) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError oe: errors.getAllErrors()
             ) {
            log.error(oe.getDefaultMessage());
        }

        sb.append(errors.getAllErrors().get(0).getDefaultMessage());
        return sb.toString();
    }

}
