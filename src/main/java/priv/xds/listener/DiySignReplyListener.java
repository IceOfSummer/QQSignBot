package priv.xds.listener;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.annotation.RoleCheck;
import priv.xds.exception.MaxAllowedReplyCountException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.service.SignReplyService;
import priv.xds.util.GroupUtil;
import priv.xds.util.MessageUtil;

import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2021-12-30 22:59
 */
@Controller
@Slf4j
public class DiySignReplyListener {

    private SignReplyService signReplyService;

    @Autowired
    public void setSignReplyService(SignReplyService signReplyService) {
        this.signReplyService = signReplyService;
    }

    @OnGroup
    @Filter(value = "自定义打卡回复", matchType = MatchType.STARTS_WITH, trim = true)
    public void addDiyReply(GroupMsg groupMsg, MsgSender sender) {
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        String message = groupMsg.getMsg().replace("自定义打卡回复", "");
        try {
            signReplyService.addReply(groupCode, message);
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "添加成功");
        } catch (MaxAllowedReplyCountException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "添加失败, 每个群最多只能自定义20条,后续提供删除功能");
        } catch (Exception e) {
            log.error("自定义打卡回复", e);
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "添加失败!请等待后续的修复!");
        }
    }
}
