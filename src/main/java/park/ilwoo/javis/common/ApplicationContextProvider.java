package park.ilwoo.javis.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContext 객체를 얻기 위한 Class
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
