package priv.xds.util;

import java.util.Random;

/**
 * @author DeSen Xu
 * @date 2021-09-19 15:50
 */
public class IpUtil {
    /**
     * 获取随机ip
     * @return 随机ip
     */
    public static String getRandomIp() {
        // ip范围
        int[][] range = {
                {607649792, 608174079},
                {1038614528, 1039007743},
                {1783627776, 1784676351},
                {2035023872, 2035154943},
                {2078801920, 2079064063},
                {-1950089216, -1948778497},
                {-1425539072, -1425014785},
                {-1236271104, -1235419137},
                {-770113536, -768606209},
                {-569376768, -564133889},
        };

        Random random = new Random();
        int index = random.nextInt(10);
        return num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    }


    private static String num2ip(int ip) {
        int[] b = new int[4];
        String ipStr;
        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        ipStr = b[0] + "." + b[1] + "." + b[2] + "." + b[3];

        return ipStr;
    }
}
