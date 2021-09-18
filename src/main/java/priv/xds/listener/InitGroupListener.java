package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.annotation.RoleCheck;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.service.UserService;
import priv.xds.util.MessageUtil;

/**
 * 用于初始化群组
 * @author DeSen Xu
 * @date 2021-09-18 20:06
 */
@Controller
public class InitGroupListener {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 初始化群组
     */
    @OnGroup
    @RoleCheck(2)
    @Filter(value = "注册群组", matchType = MatchType.EQUALS)
    public void initGroup(GroupMsg groupMsg, MsgSender sender) {
        try {
            int i = userService.initGroup(groupMsg.getGroupInfo().getGroupCode(), sender.GETTER.getGroupMemberList(groupMsg));
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "新注册了: " + i + "个人");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "所有人都已经注册了!");
        }
    }
}
