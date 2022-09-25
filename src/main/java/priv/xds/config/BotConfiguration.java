package priv.xds.config;

import love.forte.simbot.bot.BotManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.xds.mapper.UserMapper;
import priv.xds.task.SignTask;

/**
 * @author DeSen Xu
 * @date 2022-09-25 21:23
 */
@EnableConfigurationProperties(BotConfigurationProperties.class)
@Configuration
public class BotConfiguration {

    @ConditionalOnProperty(value = "qqbot.enableSignTask", havingValue = "true")
    @Bean
    public SignTask signTask(BotManager botManager, UserMapper userMapper) {
        return new SignTask(botManager, userMapper);
    }


}
