package priv.xds.util;

import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;

import java.util.NoSuchElementException;

/**
 * @author "DeSen Xu"
 * @date 2021-09-15 12:30
 */
public class GroupUtil {

    /**
     * 从群里获取指定qq的详细信息
     * @param qq qq
     * @param groupMsg 群
     * @param sender sender
     * @return 该用户信息
     * @throws NoSuchElementException 没有找到该用户
     */
    public static GroupAccountInfo getTargetUser(String qq, GroupMsg groupMsg, MsgSender sender) throws NoSuchElementException {
        return sender.GETTER.getMemberInfo(groupMsg.getGroupInfo().getGroupCode(), qq);
    }

    /**
     * 发送没有找到用户的信息
     * @param groupMsg groupMsg
     * @param sender sender
     */
    public static void sendNotFoundUser(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "无法找到目标用户!请检查后重新输入");
    }
}
