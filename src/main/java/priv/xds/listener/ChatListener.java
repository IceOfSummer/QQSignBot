package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于平常聊天
 * @author HuPeng
 * @date 2021-09-13 22:34
 */
@Component
public class ChatListener {

    @Value("${app.version}")
    private String version;


    @OnGroup
    @Priority(PriorityConstant.LAST)
    @Filter(value = "^\\s*", atBot = true, matchType = MatchType.REGEX_MATCHES)
    public void replyAt(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, "at我发送'帮助'获取更多支持!\ngithub地址:https://github.com/HuPeng333/QQBot\n当前版本: " + version);
    }

    @OnGroup
    @Filter(atBot = true, value = "帮助", matchType = MatchType.CONTAINS)
    public void help(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, "- 打卡: 群内用户输入打卡完成每日打卡功能\n" +
                "- 忽略 *QQ号* : 仅群内管理员或群主可用，可以让机器人不再统计该用户的打卡情况\n" +
                "- 取消忽略 *QQ号* :  仅群内管理员或群主可用，可以让机器人重新统计该用户的打卡情况\n" +
                "- 提醒未打卡: 仅群内管理员或群主可用，让机器人at所有没有打卡的人\n" +
                "- 打卡情况: 所有人可用，显示所有没有打卡的人(不会at)\n" +
                "- 手动打卡 *QQ号* : 群内管理员或群主手动为某人打卡，一般用于服务器出现bug重启后的补救措施 ");
    }

    @OnGroup
    @Filter(value = "test")
    public void test(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, "[CAT:image,file=classpath:images/signAfterNoon.jpg]");
    }



}
