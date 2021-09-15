package priv.xds.util;

import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.events.GroupMsg;

/**
 * @author HuPeng
 * @date 2021-09-13 12:45
 */
public class MessageUtil {
    /**
     * at一个人
     * @param code 某人的qq
     * @return 猫猫码
     */
    public static String atSomeone(Object code) {
        return "[CAT:at,code=" + code + "]";
    }

    public static String atSomeone(GroupMsg groupMsg) {
        return atSomeone(groupMsg.getAccountInfo().getAccountCode());
    }

    /**
     * 发送网络图片
     * @param url 网络图片路径
     * @return 猫猫码
     */
    public static String sendImageByUrl(String url) {
        return "[CAT:image,url=" + url + "]";
    }

    /**
     * 发送本地图片
     * @param path 本地图片的位置
     * @return  猫猫码
     */
    public static String sendImageByFile(String path) {
        return "[CAT:image,file=" + path + "]";
    }

    /**
     * 将用户名和昵称结合
     * @param info info
     * @return 返回形式: qq(昵称)
     */
    public static String combineQqAndNickname(GroupAccountInfo info) {
        String nickName = info.getAccountRemark() == null ? info.getAccountNickname() : info.getAccountRemark();
        return info.getAccountCode() + "(" + nickName + ")";
    }

}
