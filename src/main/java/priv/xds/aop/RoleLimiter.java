package priv.xds.aop;

import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.annotation.RoleCheck;
import priv.xds.exception.MissingParamException;
import priv.xds.service.UserService;
import priv.xds.util.MessageUtil;

/**
 * 用于限制用户权限
 * @see RoleCheck
 * @author "DeSen Xu"
 * @date 2021-09-14 12:35
 */
@Aspect
@Component
public class RoleLimiter {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("@annotation(priv.xds.annotation.RoleCheck)")
    public void getMaxReplyAnnotation(){}

    @Around("getMaxReplyAnnotation() && @annotation(role)")
    public Object around(ProceedingJoinPoint joinPoint, RoleCheck role) throws Throwable {
        GroupMsg msg = null;
        MsgSender sender = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof GroupMsg) {
                msg = (GroupMsg) arg;
            } else if (arg instanceof MsgSender) {
                sender = (MsgSender) arg;
            }
        }
        if (msg == null) {
            throw new MissingParamException("在监听器的参数中无法找到GroupMsg的实现类");
        }
        int curRole;
        if (msg.getPermission().isAdmin() || msg.getPermission().isOwner()) {
            // 管理员或群主权限为2
            curRole = 2;
        } else {
            curRole = userService.getUserRole(msg.getAccountInfo().getAccountCode(), msg.getGroupInfo().getGroupCode());
        }
        if (curRole >= role.value()) {
            // 可以执行
            return joinPoint.proceed();
        } else {
            // 不可执行
            if (sender != null) {
                sender.SENDER.sendGroupMsg(msg, MessageUtil.atSomeone(msg) + "宝,你的权限不够");
            }
        }
        return null;
    }
}
