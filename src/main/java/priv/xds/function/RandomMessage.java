package priv.xds.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.config.BotReplyProperties;

import java.util.Calendar;
import java.util.List;

/**
 * 获取随机的回复语
 * @author DeSen Xu
 * @date 2021-09-16 16:39
 */
@Component
public class RandomMessage {
    
    BotReplyProperties botReplyProperties;

    @Autowired
    public void setBotReplyProperties(BotReplyProperties botReplyProperties) {
        this.botReplyProperties = botReplyProperties;
    }

    /**
     * 获取随机回复
     * @return 随机回复
     */
    public String getRandomReply() {
        int index = getCurrentTimeIndex();
        List<String> replies = botReplyProperties.getSignReply().get(index);
        int maxLen = replies.size();
        return replies.get((int) (Math.random() * maxLen));
    }


    /**
     * 获取当前时间对应BotReplyProperties.signReply的数组下标
     * @return 属性下标
     */
    private int getCurrentTimeIndex() {
        Calendar instance = Calendar.getInstance();
        // 一共有多少分钟
        int totalMin = instance.get(Calendar.HOUR_OF_DAY) * 60 + instance.get(Calendar.MINUTE);
        for (int i = 0; i < BotReplyProperties.COMPARE_LIST.length; i++) {
            if (totalMin < BotReplyProperties.COMPARE_LIST[i]) {
                return i;
            }
        }
        return 0;
    }

}
