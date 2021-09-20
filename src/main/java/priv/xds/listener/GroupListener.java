package priv.xds.listener;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.OnGroupMemberReduce;
import love.forte.simbot.api.message.events.GroupMemberReduce;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.service.UserService;

/**
 * 用于监听群事件
 * @author DeSen Xu
 * @date 2021-09-20 22:36
 */
@Controller
@Slf4j
public class GroupListener {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @OnGroupMemberReduce
    public void deleteUser(GroupMemberReduce reduce) {
        log.info("删除用户: " + reduce.getAccountInfo().getAccountCode() + "在群" + reduce.getGroupInfo().getGroupCode() + "的数据");
        userService.deleteUser(reduce.getAccountInfo().getAccountCode(), reduce.getGroupInfo().getGroupCode());
    }
}
