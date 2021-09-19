package priv.xds.service;

import priv.xds.exception.FailToExecuteException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.AutoSign;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:24
 */
public interface AutoSignService {

    /**
     * 注册自动打卡
     * @param autoSign autoSign
     */
    void registerAutoSign(AutoSign autoSign);

    /**
     * 修改自动打卡的状态
     * @param qq qq号
     * @param active 是否激活
     * @throws UnNecessaryInvokeException 已经是指定的状态了
     */
    void changeAutoSignStatus(String qq, boolean active) throws UnNecessaryInvokeException;

    /**
     * 修改用户提交的token
     * @param qq qq
     * @param token token
     * @throws NoTargetValueException 没有找到用户的信息
     */
    void modifyToken(String qq, String token) throws NoTargetValueException;

    /**
     * 打卡
     * @param qq qq号
     * @throws NoTargetValueException 用户没有注册
     * @throws FailToExecuteException token失效
     */
    void sign(String qq) throws NoTargetValueException, FailToExecuteException;

    /**
     * 自动打卡注册服务
     * @param autoSign 用户信息
     * @return 返回结果执行信息
     */
    String sign(AutoSign autoSign);

    /**
     * 获取没有打卡的用户
     * @return 没有打卡的用户
     */
    List<AutoSign> getUnSignedUser();

    /**
     * 获取当前提交的信息
     * @param qq qq号
     * @return 账号信息(无法获取token)
     * @throws NoTargetValueException 没有找到保存的信息
     */
    AutoSign getInfo(String qq) throws NoTargetValueException;

}
