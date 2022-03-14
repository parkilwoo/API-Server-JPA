package park.ilwoo.javis.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

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

}
