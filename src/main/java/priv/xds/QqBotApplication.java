package priv.xds;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import priv.xds.util.BeanUtil;

/**
 * @author HuPeng
 */
@SpringBootApplication
@SimbotApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableSimbot
@EnableScheduling
@MapperScan("priv.xds.mapper")
public class QqBotApplication {

    public static void main(String[] args) {
        BeanUtil.applicationContext = SpringApplication.run(QqBotApplication.class, args);
    }

}
