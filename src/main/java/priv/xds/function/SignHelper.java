package priv.xds.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import priv.xds.pojo.AutoSign;
import priv.xds.pojo.Weather;
import priv.xds.util.IpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助打卡
 * @author DeSen Xu
 * @date 2021-09-19 11:17
 */
public class SignHelper {

    private final AutoSign autoSign;

    public static final String REPORT_URL = "https://jk.wtu.edu.cn/health/mobile/health_report/";

    public SignHelper(AutoSign autoSign) {
        this.autoSign = autoSign;
    }

    /**
     * 发送请求
     * @return 是否请求成功
     */
    public Resp send() throws IOException {
        // 发送http请求
        try (CloseableHttpClient httpClient = HttpClients.createMinimal()) {
            HttpPost httpPost = new HttpPost(REPORT_URL);
            httpPost.setHeader("Host", "jk.wtu.edu.cn");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("authorization", autoSign.getToken());
            httpPost.setHeader("charset", "utf-8");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10; DVC-AN20 Build/HUAWEIDVC-AN20; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2853 MMWEBSDK/20210601 Mobile Safari/537.36 MMWEBID/3467 wxwork/3.1.16.17857 MicroMessenger/8.0.7.48(0x28000730) MiniProgramEnv/android Luggage/1.6.0.bbb933f7 NetType/WIFI Language/zh_CN ABI/arm64");
            httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Accept-Encoding", "gzip,compress,br,deflate");
            httpPost.setHeader("Referer", "https://servicewechat.com/wx186658badc0a17c7/12/page-frame.html");
            httpPost.setHeader("X-Forwarded-For", IpUtil.getRandomIp());
            // params
            List<NameValuePair> params = new ArrayList<>();
            // 用于随机生成经纬度
            int random = (int) (Math.random() * 10);
            params.add(new BasicNameValuePair("yhm", autoSign.getYhm()));
            params.add(new BasicNameValuePair("yhlx", "student"));
            params.add(new BasicNameValuePair("jkklx", "已返校"));
            params.add(new BasicNameValuePair("jkmdqzt", "green"));
            params.add(new BasicNameValuePair("longitude", "114.3423695689669" + random));
            params.add(new BasicNameValuePair("latitude", "30.38024845402080" + random));
            params.add(new BasicNameValuePair("address", "武汉纺织大学(阳光校区)-湖北省武汉市江夏区"));
            params.add(new BasicNameValuePair("data", "{\"lxdh\":\"" + autoSign.getLxdh() + "\",\"jjlxr\":\""+ autoSign.getJjlxr() +"\",\"jjlxdh\":\"" + autoSign.getJjlxdh() +"\",\"zgdy\":\"否\",\"hbjkmzt\":\"绿色\",\"dqstzk\":\"健康\",\"dqtw\":\"36.7\",\"brsffr\":\"否\",\"brsfks\":\"否\",\"jrsffr\":\"否\",\"jrsfks\":\"否\",\"xxqk\":\"\"}\n"));
            params.add(new BasicNameValuePair("normal", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            try (CloseableHttpResponse resp = httpClient.execute(httpPost)) {
                HttpEntity entity = resp.getEntity();
                return JSON.parseObject(entity.getContent(), Resp.class, Feature.AutoCloseSource);
            }
        }
    }

    @Data
    public static class Resp{
        private String message;
        private String errorCode;

        @Override
        public String toString() {
            return message;
        }
    }
}
