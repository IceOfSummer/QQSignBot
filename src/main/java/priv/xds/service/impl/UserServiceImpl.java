package priv.xds.service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.xds.exception.NoRepeatableException;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.User;
import priv.xds.service.UserService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author HuPeng
 * @date 2021-09-12 23:47
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sign(String qq) throws Exception {
        User user = userMapper.queryUserByQq(qq);
        if (user == null) {
            // 没找到用户，先注册
            User newUser = new User();
            newUser.setQq(qq);
            newUser.setLastSign(new Date());
            // 第一次签到，签到天数自动设置为1
            newUser.setConsecutiveSignDays(1);
            int i = userMapper.addUser(newUser);
            if (i == 0) {
                throw new Exception("添加用户失败");
            }
            return 1;
        }
        // 查看是否已经注册
        Instant ins = Instant.now();
        Instant lastSign = Instant.ofEpochMilli(user.getLastSign().getTime());
        long l = Duration.between(ins, lastSign).toDays();
        if (l == 0) {
            throw new NoRepeatableException("不能重复签到");
        }
        int curSignDays = user.getConsecutiveSignDays();
        curSignDays++;
        userMapper.updateSignDays(qq, curSignDays, new Date());
        return curSignDays;
    }
}
