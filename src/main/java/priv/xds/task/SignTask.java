package priv.xds.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.results.SimpleGroupInfo;
import love.forte.simbot.api.sender.BotSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.User;
import priv.xds.util.MessageUtil;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清除群打卡的排名
 * @author HuPeng
 * @date 2021-09-13 12:19
 */
@Slf4j
public class SignTask {

    private final BotManager botManager;

    private final UserMapper userMapper;

    public SignTask(BotManager botManager, UserMapper userMapper) {
        this.botManager = botManager;
        this.userMapper = userMapper;
    }

    /**
     * 提醒没打卡的人打卡
     */
    @Scheduled(cron = "0 0 10,11,12 * * ?")
    public void tipSign() {
        log.info("开始提醒未打卡的人");
        BotSender sender = botManager.getDefaultBot().getSender();
        List<User> allUnsignedUser = userMapper.getAllUnsignedUser();
        for (SimpleGroupInfo simpleGroupInfo : botManager.getDefaultBot().getSender().GETTER.getGroupList().stream().collect(Collectors.toList())) {
            try {
                String groupCode = simpleGroupInfo.getGroupCode();
                List<User> collect = allUnsignedUser.stream().filter(user -> user.getGroupCode().equals(groupCode)).collect(Collectors.toList());
                int size = collect.size();
                log.info("群: " + groupCode + "有" + size + "个人没有打卡");
                if (size == 0) {
                    sender.SENDER.sendGroupMsg(groupCode, "好耶!今天所有人都打卡了" + MessageUtil.sendImageByFile("classpath:images/happy.jpg"));
                    continue;
                }
                StringBuilder builder = new StringBuilder(size * 20);

                for (User user : collect) {
                    builder.append(MessageUtil.atSomeone(user.getQq()))
                            .append(" ");
                }
                builder.append("记得打卡!!").append(MessageUtil.sendImageByFile("classpath:images/cuteCat2.gif"));
                sender.SENDER.sendGroupMsg(groupCode, builder.toString());
            } catch (IllegalStateException ignored) {
                // 因为机器人好友或者QQ群列表是以缓存的形式保存的,所以有可能得到的群不存在了
            }
        }
        log.info("提醒完毕");

    }

    /**
     * 提醒打卡
     */
    @Scheduled(cron = "10 0 0 * * ?")
    public void clearRank() {
        log.info("重置所有群组的打卡");
        BotSender bot = botManager.getDefaultBot().getSender();
        for (SimpleGroupInfo simpleGroupInfo : bot.GETTER.getGroupList().stream().collect(Collectors.toList())) {
            try {
                bot.SENDER.sendGroupMsg(simpleGroupInfo.getGroupCode(), "[CAT:at,all=true]打卡开始了!");
            } catch (IllegalStateException ignored) {}
        }
    }
}
