package priv.xds.util;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author HuPeng
 * @date 2021-09-13 0:38
 */
public class BeanUtil {

    public static ConfigurableApplicationContext applicationContext;

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        BeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }


}
