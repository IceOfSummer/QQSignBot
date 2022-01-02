package priv.xds;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import priv.xds.function.WeatherForecaster;

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
        SpringApplication.run(QqBotApplication.class, args);
    }

}
