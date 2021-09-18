package priv.xds.service;

import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;

/**
 * @author DeSen Xu
 * @date 2021-09-18 16:30
 */
public interface StatisticService {

    /**
     * 提交统计
     * @param content 内容
     * @param qq qq号
     * @param groupCode 群号
     * @throws UnNecessaryInvokeException 群没有开启统计
     * @throws NoRepeatableException 不允许重复提交
     */
    void submitContent(String content, String qq, String groupCode) throws UnNecessaryInvokeException, NoRepeatableException;

}
