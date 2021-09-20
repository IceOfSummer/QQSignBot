package priv.xds.function;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:33
 */
public class UrlTest {

    @Test
    public void t() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("你好", "UTF-8"));
//        URLEncodedUtils.
    }
}
