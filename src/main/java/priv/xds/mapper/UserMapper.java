package priv.xds.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.xds.pojo.User;

import java.util.Date;
import java.util.List;

/**
 * @author HuPeng
 * @date 2021-09-12 23:26
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户
     * @param qq 用户qq号
     * @param groupCode qq群
     * @return 用户信息
     */
    User queryUser(@Param("qq") String qq, @Param("groupCode") String groupCode);

    /**
     * 更新签到天数
     * @param qq 要修改的qq
     * @param signDays 连续签到天数
     * @param lastSign 最后签到时间
     */
    void updateSignDays(@Param("qq") String qq, @Param("signDays") int signDays, @Param("lastSign") Date lastSign);

    /**
     * 添加一个用户
     * @param user 要添加的用户
     * @return 返回1表示更新成功，0表示失败
     */
    int addUser(User user);

    /**
     * 批量添加数据
     * @param users 用户数据
     */
    void addUsers(List<User> users);

    /**
     * 获取所有没有打卡的人
     * @param date 今天的日期，以该日期为准，获取该日期内没有打卡的人
     * @return 没有打卡的人
     */
    List<User> getUnsignedUser(@Param("date") Date date);

    /**
     * 获取指定群号未打卡的人
     * @param date 今天的日期，以该日期为准，获取该日期内没有打卡的人
     * @param groupCode 指定群号
     * @return 未打卡的人
     */
    List<User> getUnsignedUserByGroup(@Param("date") Date date, @Param("groupCode") String groupCode);

    /**
     * 获取一个群里面的所有人
     * @param groupCode 群号
     * @return 群里面的所有人
     */
    List<User> getAllUsers(@Param("groupCode") String groupCode);

}
