package priv.xds.service;

import love.forte.simbot.api.message.results.GroupMemberList;
import org.springframework.lang.Nullable;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.User;
import java.util.List;

/**
 * @author HuPeng
 * @date 2021-09-12 23:43
 */
public interface UserService {

    /**
     * 签到,若成员在数据库无法找到,将会为TA自动注册
     * @param qq 要签到的qq
     * @param groupCode qq群
     * @throws NoRepeatableException 用户重复签到
     * @throws UnNecessaryInvokeException 用户已经被忽略了,可以不用签到
     */
    void sign(String qq, String groupCode) throws UnNecessaryInvokeException, NoRepeatableException;

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

    /**
     * 重新添加群组的人员
     * @param groupCode 群号
     * @param groupMemberList 群内的人员
     * @throws UnNecessaryInvokeException 已经全部添加了
     * @return 添加了多少人
     */
    int initGroup(String groupCode, GroupMemberList groupMemberList) throws UnNecessaryInvokeException;

    /**
     * 删除某个用户
     * @param qq qq号
     * @param groupCode 群号
     */
    void deleteUser(String qq, String groupCode);

}
