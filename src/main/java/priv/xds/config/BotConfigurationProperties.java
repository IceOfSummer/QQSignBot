package priv.xds.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DeSen Xu
 * @date 2022-09-25 21:22
 */
@ConfigurationProperties(prefix = "qqbot")
public class BotConfigurationProperties {

    private boolean enableSignTask = true;


    public boolean isEnableSignTask() {
        return enableSignTask;
    }

    public void setEnableSignTask(boolean enableSignTask) {
        this.enableSignTask = enableSignTask;
    }
}
