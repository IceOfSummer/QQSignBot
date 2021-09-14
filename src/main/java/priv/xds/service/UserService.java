package priv.xds.service;

import priv.xds.exception.NoRepeatableException;
import priv.xds.pojo.User;

import java.util.List;

/**
 * @author HuPeng
 * @date 2021-09-12 23:43
 */
public interface UserService {

    /**
     * 签到
     * @param qq 要签到的qq
     * @param groupCode qq群
     * @throws NoRepeatableException 用户重复签到
     * @throws Exception 服务端错误
     * @return 签到完毕后的天数
     */
    int sign(String qq, String groupCode) throws Exception;

    /**
     * 获取没有打卡的用户
     * @param groupCode qq群
     * @return 没有打卡的用户
     */
    List<User> getUnsignedUsers(String groupCode);

    /**
     * 查询用户权限等级
     * @param qq qq号
     * @param groupCode 群号
     * @return 权限等级
     */
    int getUserRole(String qq, String groupCode);
}
