package priv.xds.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.bot.BotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.xds.mapper.GroupMapper;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.Group;
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
@Slf4j
public class SignTask {

    private BotManager botManager;

    private UserMapper userMapper;

    private GroupMapper groupMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    /**
     * 提醒没打卡的人打卡
     */
    @Scheduled(cron = "0 0 10,11,12 * * ?")
    public void tipSign() {
        log.info("开始提醒未打卡的人");
        for (Group group : groupMapper.getAllRegisteredGroup()) {
            String groupCode = group.getGroupCode();
            // 获取没有打卡的人
            List<User> unsignedUserByGroup = userMapper.getUnsignedUserByGroup(new Date(System.currentTimeMillis()), groupCode);
            log.info("今天有" + unsignedUserByGroup.size() + "个人没有打卡");
            if(unsignedUserByGroup.isEmpty()) {
                botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(groupCode, "好耶,今天所有人都打卡了!");
                return;
            }
            StringBuilder builder = new StringBuilder(30);
            for (User user : unsignedUserByGroup) {
                builder.append(MessageUtil.atSomeone(user.getQq()));
                builder.append(" ");
            }
            builder.append(" 记得打卡!");
            botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(groupCode, builder.toString());
        }
    }


    /**
     * 清除打卡缓存
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearRank() {
        log.info("已经重置打卡");
        for (Group group : groupMapper.getAllRegisteredGroup()) {
            botManager.getDefaultBot().getSender().SENDER.sendGroupMsg(group.getGroupCode(), "[CAT:at,all=true]已经可以开始打卡了!");
        }
    }
}
