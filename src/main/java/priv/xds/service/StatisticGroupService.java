package priv.xds.service;

import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.Statistic;
import priv.xds.pojo.StatisticGroup;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-09-18 13:36
 */
public interface StatisticGroupService {

    /**
     * 为某个群开启统计
     * @param groupCode 群号
     * @param name 统计的名称
     * @throws NoRepeatableException 该群已经开启了统计
     * @return 创建成功后的id
     */
    int launchStatistic(String groupCode, String name) throws NoRepeatableException;

    /**
     * 停止某个群的统计
     * @param id 统计的id
     * @param groupCode 群号
     * @throws UnNecessaryInvokeException 该群没有开启统计
     */
    void stopStatistic(int id, String groupCode) throws UnNecessaryInvokeException;

    /**
     * 获取统计的结果
     * @param groupCode 群号
     * @param id 统计的id
     * @throws NoTargetValueException 该群没有开启统计
     * @return 统计结果
     */
    List<Statistic> getStatisticInfo(int id, String groupCode) throws NoTargetValueException;

    /**
     * 获取所有存在的统计
     * @param groupCode 群号
     * @return 统计结果
     */
    List<StatisticGroup> getAllStatistic(String groupCode);

}
