package priv.xds.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.xds.pojo.Statistic;

/**
 * 用于存放用户提交信息的表
 * @author DeSen Xu
 * @date 2021-09-18 15:14
 */
@Mapper
public interface StatisticMapper {

    /**
     * 增加用户提交的信息
     * @param content 内容
     * @param qq qq号
     * @param statisticGroupId 统计的id
     */
    void addContent(@Param("content") String content, @Param("qq") String qq, @Param("statisticGroupId") int statisticGroupId);

    /**
     * 获取某个用户回复的内容
     * @param qq qq号
     * @param statisticGroupId 统计编号
     * @return 回复内容
     */
    Statistic getStatistic(@Param("qq") String qq, @Param("statisticGroupId") int statisticGroupId);
}
