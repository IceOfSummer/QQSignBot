package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.annotation.RoleCheck;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.function.RandomMessage;
import priv.xds.pojo.User;
import priv.xds.service.UserService;
import priv.xds.util.GroupUtil;
import priv.xds.util.MessageUtil;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author HuPeng
 * @date 2021-09-12 22:47
 */
@Controller
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
            userService.sign(accountInfo.getAccountCode(), groupMsg.getGroupInfo().getGroupCode());
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + randomMessage.getRandomReply());
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) +"打卡失败!请不要连续打卡");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "你已经被忽略了,不会被记入打卡统计!");
        }
    }

    @OnGroup
    @Filter(value = "提醒未打卡", matchType = MatchType.EQUALS)
    public void getUnsigned(GroupMsg groupMsg, MsgSender sender) {
        boolean admin = groupMsg.getPermission().isAdmin() || groupMsg.getPermission().isOwner();
        if (admin) {
            List<User> unsignedUsers = userService.getUnsignedUsers(groupMsg.getGroupInfo().getGroupCode());
            if (unsignedUsers == null) {
                sender.SENDER.sendGroupMsg(groupMsg, "真好,今天所有人都打卡了");
            } else {
                StringBuilder builder = new StringBuilder(20);
                for (User unsignedUser : unsignedUsers) {
                    builder.append(MessageUtil.atSomeone(unsignedUser.getQq()));
                    builder.append(" ");
                }
                builder.append(" 记得打卡!");
                sender.SENDER.sendGroupMsg(groupMsg, builder.toString());
            }
        } else {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "宝,你的权限不足!");
        }
    }

    @OnGroup
    @Filter(value = "^忽略 \\d+", matchType = MatchType.REGEX_FIND, trim = true)
    @RoleCheck(2)
    public void ignoreSomeone(GroupMsg groupMsg, MsgSender sender) {
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        String qq = splitQq(groupMsg);
        String accountNickname = null;
        try {
            accountNickname = sender.GETTER.getMemberInfo(groupCode, qq).getAccountNickname();
        } catch (NoSuchElementException e) {
            GroupUtil.sendNotFoundUser(groupMsg, sender);
        }
        try {
            userService.ignoreUser(qq, groupCode);
            sender.SENDER.sendGroupMsg(groupMsg, "已经忽略对" + qq + "(" + accountNickname +")的打卡统计");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, qq + "(" + accountNickname +")已经被忽略了");
        }
    }

    @OnGroup
    @Filter(value = "^取消忽略 \\d+", matchType = MatchType.REGEX_FIND, trim = true)
    @RoleCheck(2)
    public void reStatisticsUser(GroupMsg groupMsg, MsgSender sender) {
        String qq = splitQq(groupMsg);
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        String accountNickname;
        try {
            accountNickname = sender.GETTER.getMemberInfo(groupCode, qq).getAccountNickname();
        } catch (NoSuchElementException e) {
            GroupUtil.sendNotFoundUser(groupMsg, sender);
            return;
        }
        try {
            userService.reStatisticsUser(qq, groupCode);
            sender.SENDER.sendGroupMsg(groupMsg, "已经重新开始统计" + qq + "(" + accountNickname +")的打卡统计");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, qq + "(" + accountNickname +")仍然还在统计");
        }
    }

    /**
     * 从指令分类出要操作的QQ
     * @param groupMsg 发送的信息对象
     * @return 指定的qq
     */
    private String splitQq(GroupMsg groupMsg) {
        String text = groupMsg.getText();
        return text.split(" ")[1];
    }

    @OnGroup
    @Filter(value = "打卡情况", matchType = MatchType.EQUALS, trim = true)
    public void getUnsignedUser(GroupMsg groupMsg, MsgSender sender) {
        List<User> unsignedUsers = userService.getUnsignedUsers(groupMsg.getGroupInfo().getGroupCode());
        if (unsignedUsers == null) {
            sender.SENDER.sendGroupMsg(groupMsg, "真好,今天所有人都打卡了");
            return;
        }
        StringBuilder builder = new StringBuilder(20);
        builder.append("目前还没打卡的人: ");
        for (User unsignedUser : unsignedUsers) {
            GroupMemberInfo memberInfo = sender.GETTER.getMemberInfo(groupMsg.getGroupInfo().getGroupCode(), unsignedUser.getQq());
            builder
                    .append(MessageUtil.combineQqAndNickname(memberInfo));
        }
        sender.SENDER.sendGroupMsg(groupMsg, builder.toString());
    }

    @OnGroup
    @Filter(value = "^代打卡 \\d+", matchType = MatchType.REGEX_MATCHES, trim = true)
    @RoleCheck(2)
    public void signByAdmin(GroupMsg groupMsg, MsgSender sender) {
        String qq = splitQq(groupMsg);
        // 检查用户是否存在
        GroupAccountInfo targetUser;
        try {
            targetUser = GroupUtil.getTargetUser(qq, groupMsg, sender);
        } catch (NoSuchElementException e) {
            GroupUtil.sendNotFoundUser(groupMsg, sender);
            return;
        }
        // 签到
        try {
            userService.sign(qq, groupMsg.getGroupInfo().getGroupCode());
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "成功帮" + MessageUtil.combineQqAndNickname(targetUser) + "打卡");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + MessageUtil.combineQqAndNickname(targetUser) + "被忽略了");
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + MessageUtil.combineQqAndNickname(targetUser) + "已经签到了");
        }
    }


}
