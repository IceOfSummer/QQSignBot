package priv.xds.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.xds.pojo.AutoSign;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:53
 */
@Mapper
public interface AutoSignMapper {

    /**
     * 为某个账号注册自动打卡
     * @param autoSign autoSign
     */
    void resistAutoSign(AutoSign autoSign);

    /**
     * 更新自动打卡
     * @param autoSign autoSign
     * @return 1表示成功,0表示失败
     */
    int updateAutoSign(AutoSign autoSign);

    /**
     * 更新激活状态
     * @param qq qq号
     * @param active 激活状态
     * @return 1表示成功,0表示失败
     */
    int updateLaunchStatus(@Param("qq") String qq, @Param("active") boolean active);

    /**
     * 获取记录的用户信息
     * @param qq 用户
     * @return 用户信息
     */
    AutoSign getUser(@Param("qq") String qq);

    /**
     * 获取没有打卡的用户
     * @return 没有打卡的用户(一次最多获取10人!)
     */
    List<AutoSign> getUnSignedUser();

}
