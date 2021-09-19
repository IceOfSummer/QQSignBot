package priv.xds.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
        if (isAllDone) {
            // 没有要帮助的了
            return;
        }
        Sender sender = botManager.getDefaultBot().getSender().SENDER;
        List<AutoSign> unSignedUser = autoSignService.getUnSignedUser();
        if (unSignedUser.isEmpty()) {
            isAllDone = true;
            return;
        }
        for (AutoSign autoSign : unSignedUser) {
            String sign = autoSignService.sign(autoSign);
            sender.sendPrivateMsg(autoSign.getQq(), sign);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void reset() {
        isAllDone = false;
    }
}
