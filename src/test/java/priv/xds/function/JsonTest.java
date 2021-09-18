package priv.xds.function;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import priv.xds.pojo.Weather;

/**
 * @author DeSen Xu
 * @date 2021-09-17 18:00
 */
public class JsonTest {

    @Test
    public void t() {
        String data = "{     \"cityid\":\"101120101\",     \"city\":\"济南\",     \"update_time\":\"20:55\",     \"wea\":\"晴\",     \"wea_img\":\"qing\",     \"tem\":\"11\",     \"tem_day\":\"17\",     \"tem_night\":\"7\",     \"win\":\"东南风 \",     \"win_speed\":\"1级\",     \"win_meter\":\"小于12km/h\",     \"air\":\"73\" }";
        Weather weather = JSON.parseObject(data, Weather.class);
        System.out.println(weather);
    }

    @Test
    public void t2() {
        System.out.println(" ".trim());
    }


}
