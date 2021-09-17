package priv.xds.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-09-17 17:46
 */
@Data
public class Weather {
    /**
     * 天气
     */
    @JSONField(name = "wea")
    private String weather;

    /**
     * 最高温度
     */
    @JSONField(name = "tem1")
    private String maxTemperature;

    /**
     * 最低温度
     */
    @JSONField(name = "tem2")
    private String minTemperature;

    /**
     * 风速等级
     */
    @JSONField(name = "win_speed")
    private String windSpeed;

    /**
     * 空气质量
     */
    @JSONField(name = "air")
    private String air;

    /**
     * 空气质量等级
     */
    @JSONField(name = "air_level")
    private String airLevel;

    /**
     * 空气质量建议
     */
    @JSONField(name = "air_tips")
    private String airTips;

    /**
     * 湿度
     */
    @JSONField(name = "humidity")
    private String humidity;

    @Override
    public String toString() {
        return "天气: " + weather + "\n最低温: " + minTemperature + "\n最高温: "
                + maxTemperature + "\n风速等级: " + windSpeed + "\n空气质量: " + air
                + "\n空气质量等级: " + airLevel + "\n湿度: " + humidity + "\n建议: " + airTips;
    }
}
