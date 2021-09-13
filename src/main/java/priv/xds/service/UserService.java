package priv.xds.service;

import priv.xds.exception.NoRepeatableException;

/**
 * @author HuPeng
 * @date 2021-09-12 23:43
 */
public interface UserService {

    /**
     * 签到
     * @param qq 要签到的qq
     * @throws NoRepeatableException 用户重复签到
     * @throws Exception 服务端错误
     * @return 签到完毕后的天数
     */
    int sign(String qq) throws Exception;
}
