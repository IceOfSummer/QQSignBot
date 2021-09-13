package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.stereotype.Component;

/**
 * 用于平常聊天
 * @author HuPeng
 * @date 2021-09-13 22:34
 */
@Component
public class ChatListener {

    @OnGroup
    @Filter(atBot = true)
    public void replyAt(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, " ♪（＾∀＾●）ﾉｼ");
    }

}
