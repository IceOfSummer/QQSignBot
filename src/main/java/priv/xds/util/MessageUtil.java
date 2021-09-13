package priv.xds.util;

/**
 * @author HuPeng
 * @date 2021-09-13 12:45
 */
public class MessageUtil {
    /**
     * at一个人
     * @param code 某人的qq
     * @return 转换好的字符串
     */
    public static String atSomeone(Object code) {
        return "[CAT:at,code=" + code + "]";
    }

}
