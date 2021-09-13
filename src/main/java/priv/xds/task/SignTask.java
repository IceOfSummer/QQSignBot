package priv.xds.task;

import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.User;
import priv.xds.util.MessageUtil;

import java.sql.Date;
import java.util.List;

/**
 * 清除群打卡的排名
 * @author HuPeng
 * @date 2021-09-13 12:19
 */
@Component
public class SignTask {

    private BotManager botManager;

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    /**
     * 提醒没打卡的人打卡
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void tipSign() {
        StringBuilder builder = new StringBuilder(30);
        List<User> unsignedUser = userMapper.getUnsignedUser(new Date(System.currentTimeMillis()));
        if (unsignedUser.isEmpty()) {
            // 所有人都打卡了!
            botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(700026523L, "好耶,今天所有人都打卡了!");
            return;
        }
        for (User user : unsignedUser) {
            builder.append(MessageUtil.atSomeone(user.getQq()));
            builder.append(" ");
        }
        builder.append(" 记得打卡!");
        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(700026523L, builder.toString());
    }

    /**
     * 清除打卡缓存
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearRank() {
        botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(700026523L, "已经可以开始打卡了!");
    }
}
