package priv.xds.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author "DeSen Xu"
 * @date 2021-09-15 19:01
 */
@ConfigurationProperties(prefix = "bot.reply")
@Component
@PropertySource(value = "file:${user.dir}/config/botReply.yaml", factory = YamlSourceFactory.class)
public class BotReplyProperties {

    /**
     * 用于表示每个阶段各有多少分钟, 若现在为00:10,相对于00:00有10分钟,小于COMPARE_LIST[0]下的30,所以当前下标应该为1
     */
    public static final int[] COMPARE_LIST = {30, 300, 420, 480, 600, 720, 780, 1440};


    /**
     * 用于签到时返回的回复语
     * list长度<b>必须</b>为8!
     * 按照顺序表示的时间是:
     * 0 - 0:00 - 0:30
     * 1 - 0:30 - 5:00
     * 2 - 5:00 - 7:00
     * 3 - 7:00 - 8:00
     * 4 - 8:00 - 10:00
     * 5 - 10:00 - 12:00
     * 6 - 12:00 - 13:00
     * 7 - 13:00 - 23:59
     */
    private List<List<String>> signReply;

    public List<List<String>> getSignReply() {
        return signReply;
    }

    public void setSignReply(List<List<String>> signReply) {
        this.signReply = signReply;
    }
}
