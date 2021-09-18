package priv.xds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.mapper.StatisticGroupMapper;
import priv.xds.mapper.StatisticMapper;
import priv.xds.pojo.Statistic;
import priv.xds.pojo.StatisticGroup;
import priv.xds.service.StatisticService;

/**
 * @author DeSen Xu
 * @date 2021-09-18 16:30
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private StatisticMapper statisticMapper;

    private StatisticGroupMapper statisticGroupMapper;

    @Autowired
    public void setStatisticMapper(StatisticMapper statisticMapper) {
        this.statisticMapper = statisticMapper;
    }

    @Autowired
    public void setStatisticGroupMapper(StatisticGroupMapper statisticGroupMapper) {
        this.statisticGroupMapper = statisticGroupMapper;
    }

    @Override
    public void submitContent(String content, String qq, String groupCode) throws UnNecessaryInvokeException, NoRepeatableException {
        StatisticGroup activeStatistic = statisticGroupMapper.getActiveStatistic(groupCode);
        if (activeStatistic == null) {
            // 没有激活的统计
            throw new UnNecessaryInvokeException();
        }
        Statistic statistic = statisticMapper.getStatistic(qq, activeStatistic.getId());
        if (statistic == null) {
            statisticMapper.addContent(content, qq, activeStatistic.getId());
            return;
        }
        throw new NoRepeatableException();
    }
}
