package priv.xds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置哪些群组应该用于签到
 * @see priv.xds.aop.SignFilter SignFilter 使用aop过滤监听
 * @author HuPeng
 * @date 2021-09-13 10:10
 */
@Configuration
@EnableConfigurationProperties(BotSignProperties.class)
public class BotSignConfiguration {

    private final BotSignProperties botSignProperties;

    @Autowired
    public BotSignConfiguration(BotSignProperties botSignProperties) {
        this.botSignProperties = botSignProperties;
    }


}
