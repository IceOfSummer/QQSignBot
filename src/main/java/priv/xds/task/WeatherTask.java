package priv.xds.task;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.results.SimpleGroupInfo;
import love.forte.simbot.api.sender.BotSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.xds.function.WeatherForecaster;
import priv.xds.pojo.Weather;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author DeSen Xu
 * @date 2021-09-18 11:26
 */
@Component
@Slf4j
public class WeatherTask {

    private WeatherForecaster weatherForecaster;

    private BotManager botManager;

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    @Autowired
    public void setWeatherForecaster(WeatherForecaster weatherForecaster) {
        this.weatherForecaster = weatherForecaster;
    }

    /**
     * 提醒今日的天气情况
     */
    @Scheduled(cron = "0 30 7 * * ?")
    public void tipWeather() {
        log.info("开始提醒天气");
        Weather weather;
        try {
            weather = weatherForecaster.getWeather();
        } catch (IOException e) {
            log.error("天气提醒失败" + e);
            return;
        }
        BotSender bot = botManager.getDefaultBot().getSender();
        for (SimpleGroupInfo simpleGroupInfo : bot.GETTER.getGroupList().stream().collect(Collectors.toList())) {
            bot.SENDER.sendGroupMsg(simpleGroupInfo.getGroupCode(), "===今日天气===" + weather.toString());
            log.info("天气提醒: " + simpleGroupInfo.getGroupCode());
        }
        log.info("提醒完毕");
    }

}
