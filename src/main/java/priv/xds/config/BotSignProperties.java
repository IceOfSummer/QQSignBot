package priv.xds.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 用于限制哪些群组应该用于签到
 * @author HuPeng
 * @date 2021-09-13 10:11
 */
@ConfigurationProperties(prefix = "bot.sign")
@Data
public class BotSignProperties {

    /**
     * 要进行签到操作的qq群
     */
    private List<String> targetGroup;
}
