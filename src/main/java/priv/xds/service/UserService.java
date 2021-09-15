package priv.xds.service;

import org.springframework.lang.Nullable;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
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
     * @throws UnNecessaryInvokeException 用户已经被忽略了,可以不用签到
     * @return 签到完毕后的天数
     */
    int sign(String qq, String groupCode) throws UnNecessaryInvokeException, NoRepeatableException;

    /**
     * 获取没有打卡的用户
     * @param groupCode qq群
     * @return 没有打卡的用户
     */
    @Nullable
    List<User> getUnsignedUsers(String groupCode);

    /**
     * 查询用户权限等级
     * @param qq qq号
     * @param groupCode 群号
     * @return 权限等级
     */
    int getUserRole(String qq, String groupCode);

    /**
     * 忽略某个用户的打卡情况
     * @param qq qq号
     * @param groupCode 群号
     * @throws UnNecessaryInvokeException 用户已经被忽略
     */
    void ignoreUser(String qq, String groupCode) throws UnNecessaryInvokeException;

    /**
     * 重新统计某个用户的打卡情况
     * @param qq qq号
     * @param groupCode 群号
     * @throws UnNecessaryInvokeException 用户没有被忽略
     */
    void reStatisticsUser(String qq, String groupCode) throws UnNecessaryInvokeException;
}
