package priv.xds.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.AutoSign;
import priv.xds.service.AutoSignService;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-09-19 16:53
 */
@Component
@Slf4j
public class AutoSignTask {

    private AutoSignService autoSignService;

    private BotManager botManager;


    private boolean isAllDone = false;

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    @Autowired
    public void setAutoSignService(AutoSignService autoSignService) {
        this.autoSignService = autoSignService;
    }

    @Scheduled(cron = "0 20,40 1,2,3,4,5,6,11,12,13 * * ?")
    public void autoSign() {
        log.info("开始自动帮助打卡");
        if (isAllDone) {
            log.info("自动打卡任务已经完成!");
            // 没有要帮助的了
            return;
        }
        Sender sender = botManager.getDefaultBot().getSender().SENDER;
        List<AutoSign> unSignedUser = autoSignService.getUnSignedUser();
        if (unSignedUser.isEmpty()) {
            log.info("没有要自动打卡的目标,已关闭自动打卡");
            isAllDone = true;
            return;
        }
        for (AutoSign autoSign : unSignedUser) {
            log.info("正在帮助: " + autoSign.getQq() + "打卡");
            try {
                // 自动打卡(附带群内签到功能)
                String sign = autoSignService.sign(autoSign);
                sender.sendPrivateMsg(autoSign.getQq(), autoSign.getGroupCode(), sign);
            } catch (Exception e) {
                // 输出日志
                log.error(e.toString());
                sender.sendPrivateMsg(autoSign.getQq(), autoSign.getGroupCode(), e.toString());
                try {
                    // 关闭用户的自动打卡功能!
                    autoSignService.changeAutoSignStatus(autoSign.getQq(), false);
                } catch (UnNecessaryInvokeException ignored) {
                } finally {
                    sender.sendPrivateMsg(autoSign.getQq(), autoSign.getGroupCode(), "已经为你关闭自动打卡功能,在解决问题后可以重新开启\n" +
                            "此外,请不要忘记在群内打卡!");
                }

            }
        }
        log.info("自动打卡结束!");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void reset() {
        isAllDone = false;
    }
}
