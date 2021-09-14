package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.annotation.SignGroup;
import priv.xds.exception.NoRepeatableException;
import priv.xds.service.UserService;
import priv.xds.util.SignCache;

import java.util.Map;

/**
 * @author HuPeng
 * @date 2021-09-12 22:47
 */
@Component
public class SignListener {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @OnGroup
    @SignGroup
    @Filter(value = "打卡")
    public void sign(GroupMsg groupMsg,
                     MsgSender sender) {
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        Map<String, Object> cache = SignCache.getCache();
        try {
            int signDays = userService.sign(accountInfo.getAccountCode(), groupMsg.getGroupInfo().getGroupCode());
            Integer rank = (Integer) cache.get(groupMsg.getGroupInfo().getGroupCode());
            if (rank == null) {
                cache.put(groupMsg.getGroupInfo().getGroupCode(), 1);
                rank = 1;
            } else {
                cache.put(groupMsg.getGroupInfo().getGroupCode(), rank + 1);
                rank++;
            }
            sender.SENDER.sendGroupMsg(groupMsg, "[CAT:at,code="+ accountInfo.getAccountCode() +"]打卡成功!今天是第" + rank + "个打卡!目前已经连续打卡" + signDays + "天");
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, "[CAT:at,code="+ accountInfo.getAccountCode() +"]打卡失败!请不要连续打卡");
        } catch (Exception e) {
            sender.SENDER.sendGroupMsg(groupMsg, "[CAT:at,code="+ accountInfo.getAccountCode() +"]打卡失败" + e.getCause() + ":" + e.getMessage());
            e.printStackTrace();
        }
    }

}
