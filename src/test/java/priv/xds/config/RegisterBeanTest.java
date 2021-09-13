package priv.xds.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.xds.listener.SignListener;
import priv.xds.util.BeanUtil;

/**
 * @author HuPeng
 * @date 2021-09-13 10:36
 */
@SpringBootTest
public class RegisterBeanTest {

    @Test
    public void testRegisterBean() {
        System.out.println(BeanUtil.getBean(SignListener.class));
    }
}
