package priv.xds.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import priv.xds.pojo.User;
import java.util.List;

/**
 * @author HuPeng
 * @date 2021-09-12 23:26
 */
@Mapper
public interface UserMapper {

    /**
     * 打卡
     * @param qq qq号
     * @param groupCode 群号
     * @return 返回1表示成功 0表示失败
     */
    int sign(@Param("qq") String qq, @Nullable @Param("groupCode") String groupCode);


    /**
     * 查询用户
     * @param qq 用户qq号
     * @param groupCode qq群
     * @return 用户信息
     */
    User queryUser(@Param("qq") String qq, @Param("groupCode") String groupCode);


    /**
     * 添加一个用户
     * @param user 要添加的用户
     */
    void addUser(User user);

    /**
     * 批量添加数据
     * @param users 用户数据
     */
    void addUsers(List<User> users);

    /**
     * 获取所有没有打卡的人
     * @return 没有打卡的人
     */
    List<User> getAllUnsignedUser();

    /**
     * 获取指定群号未打卡的人
     * @param groupCode 指定群号
     * @return 未打卡的人
     */
    List<User> getUnsignedUserByGroup(@Param("groupCode") String groupCode);

    /**
     * 获取一个群里面的所有人
     * @param groupCode 群号
     * @return 群里面的所有人
     */
    List<User> getAllUsers(@Param("groupCode") String groupCode);

    /**
     * 忽略某个用户的打卡统计
     * @param qq qq号
     * @param groupCode 群号
     * @return 返回1表示成功, 0表示不需要修改
     */
    int ignoreUser(@Param("qq") String qq, @Param("groupCode") String groupCode);

    /**
     * 重新统计某个用户的打卡统计
     * @param qq qq号
     * @param groupCode 群号
     * @return 返回1表示成功, 0表示不需要修改
     */
    int reStatisticsUser(@Param("qq") String qq, @Param("groupCode") String groupCode);


    /**
     * 删除某个用户
     * @param qq qq号
     * @param groupCode 群号
     */
    void deleteUser(@Param("qq") String qq, @Param("groupCode") String groupCode);


}