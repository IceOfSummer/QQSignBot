package priv.xds.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import priv.xds.pojo.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2021-09-17 17:33
 */
public class WeatherForecaster {

    /**
     * 请求的url
     */
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public Weather getWeather() throws IOException {
        // 发送http请求
        try (CloseableHttpClient httpClient = HttpClients.createMinimal()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse resp = httpClient.execute(httpGet)) {
                HttpEntity entity = resp.getEntity();
                return JSON.parseObject(entity.getContent(), Weather.class, Feature.AutoCloseSource);
            }
        }
    }

}
