package priv.xds.aop;

import love.forte.simbot.api.message.events.GroupMsg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.xds.config.BotSignProperties;
import priv.xds.exception.MissingParamException;

/**
 * @author HuPeng
 * @date 2021-09-13 10:15
 */
@Aspect
@Component
public class SignFilter {


    private BotSignProperties botSignProperties;

    @Autowired
    public void setBotSignProperties(BotSignProperties botSignProperties) {
        this.botSignProperties = botSignProperties;
    }

    @Around(value = "@annotation(priv.xds.annotation.SignGroup)")
    public Object filterGroup(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        GroupMsg groupMsg = null;
        for (Object arg : args) {
            if (arg instanceof GroupMsg) {
                groupMsg = (GroupMsg) arg;
            }
        }
        if (groupMsg == null) {
            throw new MissingParamException(joinPoint.getTarget() + " 没有设置GroupMsg参数,已拒绝该监听");
        }
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        for (String s : botSignProperties.getTargetGroup()) {
            if (groupCode.equals(s)) {
                // 查询到了，正常执行
                return joinPoint.proceed();
            }
        }
        // 没查到，不执行
        return null;
    }
}
