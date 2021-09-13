package priv.xds.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存放签到用的缓存
 * @author HuPeng
 * @date 2021-09-13 12:22
 */
public class SignCache {

    private static final Map<String, Object> cache = new HashMap<>();

    public static Map<String, Object> getCache() {
        return cache;
    }
}
