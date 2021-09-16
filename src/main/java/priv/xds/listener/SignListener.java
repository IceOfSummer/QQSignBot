package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.function.RandomMessage;
import priv.xds.service.UserService;
import priv.xds.util.MessageUtil;

/**
 * @author HuPeng
 * @date 2021-09-12 22:47
 */
@Component
public class SignListener {

    private UserService userService;

    private RandomMessage randomMessage;

    @Autowired
    public void setRandomMessage(RandomMessage randomMessage) {
        this.randomMessage = randomMessage;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @OnGroup
    @Filter(value = "打卡")
    public void sign(GroupMsg groupMsg,
                     MsgSender sender) {
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        try {
            int signDays = userService.sign(accountInfo.getAccountCode(), groupMsg.getGroupInfo().getGroupCode());
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + randomMessage.getRandomReply());
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) +"打卡失败!请不要连续打卡");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "你已经被忽略了,不会被记入打卡统计!");
        }
    }

}
