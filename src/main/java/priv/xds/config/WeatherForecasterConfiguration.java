package priv.xds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.xds.function.WeatherForecaster;

import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2021-09-17 17:34
 */
@Configuration
@EnableConfigurationProperties(WeatherForecasterProperties.class)
@ConditionalOnProperty(prefix = "weather", value = "enabled", havingValue = "true")
public class WeatherForecasterConfiguration {

    private WeatherForecasterProperties weatherProperties;

    @Autowired
    public void setWeatherProperties(WeatherForecasterProperties weatherProperties) {
        this.weatherProperties = weatherProperties;
    }

    @Bean
    public WeatherForecaster getWeather() {
        WeatherForecaster weather = new WeatherForecaster();
        weather.setUrl(weatherProperties.getUrl());
        if (weatherProperties.getParams() == null || weatherProperties.getParams().size() == 0) {
            return weather;
        }
        // 拼接参数
        StringBuilder builder = new StringBuilder(20);
        builder.append("?");
        for (Map.Entry<String, String> stringStringEntry : weatherProperties.getParams().entrySet()) {
            builder
                    .append(stringStringEntry.getKey())
                    .append("=")
                    .append(stringStringEntry.getValue())
                    .append("&");
        }
        // 删掉多余的&符号
        builder.deleteCharAt(builder.length() - 1);
        weather.setUrl(weatherProperties.getUrl() + builder);
        return weather;
    }
}
