package priv.xds.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2021-09-17 17:32
 */
@ConfigurationProperties(prefix = "weather")
@Data
public class WeatherForecasterProperties {

    /**
     * 请求的url
     */
    private String url;

    /**
     * 请求所附带的参数
     */
    private Map<String, String> params;

    /**
     * 是否开启天气提醒
     */
    private boolean enabled = true;
}
