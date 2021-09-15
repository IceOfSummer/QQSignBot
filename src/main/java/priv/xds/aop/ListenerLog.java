package priv.xds.aop;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.events.GroupMsg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import priv.xds.util.MessageUtil;

/**
 * @author "DeSen Xu"
 * @date 2021-09-15 13:56
 */
@Aspect
@Component
@Slf4j
public class ListenerLog {

    @After("execution(* priv.xds.listener.*.*(..))")
    public void after(JoinPoint joinPoint) {
        GroupMsg groupMsg = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof GroupMsg) {
                groupMsg = (GroupMsg) arg;
            }
        }
        if (groupMsg != null) {
            log.info("群" + MessageUtil.combineGroupIdAndName(groupMsg) + " 用户"+ MessageUtil.combineQqAndNickname(groupMsg) + "调用了: " + joinPoint.getSignature().getName() + "方法");
        } else {
            log.info(joinPoint.getSignature().getName() + "被调用了");
        }
    }

}
