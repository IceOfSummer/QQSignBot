package priv.xds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.xds.pojo.Statistic;
import priv.xds.pojo.StatisticGroup;

import java.util.List;

/**
 * 用于映射用户存放信息的组
 * @author DeSen Xu
 * @date 2021-09-18 14:20
 */
@Mapper
public interface StatisticGroupMapper extends BaseMapper<StatisticGroup> {

    /**
     * 添加一条统计记录,添加成功后默认为激活状态
     * @param statisticGroup 要添加的记录
     */
    void addStatistic(StatisticGroup statisticGroup);

    /**
     * 删除某条统计
     * @param id id
     * @param groupCode 群号
     */
    void deleteStatistic(@Param("id") int id, @Param("groupCode") String groupCode);

    /**
     * 停止某条统计
     * @param id 统计的id
     * @param groupCode 群号
     * @return 返回1表示成功,0表示失败
     */
    int stopStatistic(@Param("id") int id, @Param("groupCode") String groupCode);

    /**
     * 获取当前统计结果
     * @param id id
     * @param groupCode 群号
     * @return 统计结果
     */
    List<Statistic> getStatistic(@Param("id") int id, @Param("groupCode") String groupCode);

    /**
     * 获取当前正在活跃的统计
     * @param groupCode 群号
     * @return 活跃的统计
     */
    List<StatisticGroup> getAllStatistic(@Param("groupCode") String groupCode);

    /**
     * 获取当前正在激活的统计
     * @param groupCode 群号
     * @return 激活的统计
     */
    StatisticGroup getActiveStatistic(@Param("groupCode") String groupCode);

}
