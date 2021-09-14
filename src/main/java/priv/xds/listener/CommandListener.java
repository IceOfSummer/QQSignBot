package priv.xds.listener;

import com.mysql.cj.protocol.MessageSender;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.annotation.RoleCheck;
import priv.xds.pojo.User;
import priv.xds.service.UserService;
import priv.xds.util.MessageUtil;

import java.util.List;

/**
 * @author "DeSen Xu"
 * @date 2021-09-14 12:14
 */
@Component
public class CommandListener {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @OnGroup
    @Filter(value = "提醒未打卡", matchType = MatchType.CONTAINS)
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
    @Filter(value = "忽略", matchType = MatchType.CONTAINS, trim = true)
    @RoleCheck(role = 3)
    public void ignoreSomeone(GroupMsg groupMsg, MsgSender sender) {
        String text = groupMsg.getText();
        if (text == null) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "用法: 忽略 qq号");
            return;
        }
        String[] s = text.split(" ");
        sender.SENDER.sendGroupMsg(groupMsg, "成功忽略对" + s[2] + "的打卡统计");
    }
}
