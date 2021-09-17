package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.stereotype.Component;

/**
 * 用于注册群服务
 * @author DeSen Xu
 * @date 2021-09-16 13:22
 */
@Component
public class RegisterListener {

    @OnGroup()
    @Filter(atBot = true, value = "重新添加群成员")
    public void addAllGroupUsers(GroupMsg groupMsg, MsgSender msgSender) {
        // TODO
    }

}
