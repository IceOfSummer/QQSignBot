package priv.xds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.mapper.StatisticGroupMapper;
import priv.xds.pojo.Statistic;
import priv.xds.pojo.StatisticGroup;
import priv.xds.service.StatisticGroupService;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-09-18 13:36
 */
@Service
public class StatisticGroupServiceImpl implements StatisticGroupService {

    private StatisticGroupMapper statisticGroupMapper;

    @Autowired
    public void setStatisticGroupMapper(StatisticGroupMapper statisticGroupMapper) {
        this.statisticGroupMapper = statisticGroupMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NoRepeatableException.class)
    public int launchStatistic(String groupCode, String name) throws NoRepeatableException {
        StatisticGroup activeStatistic = statisticGroupMapper.getActiveStatistic(groupCode);
        if (activeStatistic == null) {
            StatisticGroup statisticGroup = new StatisticGroup();
            statisticGroup.setGroupCode(groupCode);
            statisticGroup.setName(name);
            statisticGroupMapper.addStatistic(statisticGroup);
            return statisticGroup.getId();
        }
        throw new NoRepeatableException("已经有激活的统计了! id为: " + activeStatistic.getId());
    }

    @Override
    public void stopStatistic(int id, String groupCode) throws UnNecessaryInvokeException {
        if (statisticGroupMapper.stopStatistic(id, groupCode) == 0) {
            throw new UnNecessaryInvokeException();
        }
    }

    @Override
    public List<Statistic> getStatisticInfo(int id, String groupCode) {
        return statisticGroupMapper.getStatistic(id, groupCode);
    }

    @Override
    public List<StatisticGroup> getAllStatistic(String groupCode) {
        return statisticGroupMapper.getAllStatistic(groupCode);
    }
}
