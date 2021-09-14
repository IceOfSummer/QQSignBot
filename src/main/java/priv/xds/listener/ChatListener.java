package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.listener.ListenerContext;
import org.springframework.stereotype.Component;

/**
 * 用于平常聊天
 * @author HuPeng
 * @date 2021-09-13 22:34
 */
@Component
public class ChatListener {

    @OnGroup
    @Priority(PriorityConstant.LAST)
    @Filter(value = " ", atBot = true, matchType = MatchType.ENDS_WITH, trim = true)
    public void replyAt(GroupMsg groupMsg, MsgSender sender, ListenerContext listenerContext) {
        String text = groupMsg.getText();
        if (text == null || text.length() == 0 || text.length() == 1) {
            sender.SENDER.sendGroupMsg(groupMsg, " ♪（＾∀＾●）ﾉｼ");
        }
    }

    @OnGroup
    @Filter(atBot = true, value = "test", matchType = MatchType.CONTAINS)
    public void replySex(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, "[CAT:face=23]");
    }



}
