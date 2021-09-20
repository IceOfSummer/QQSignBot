package priv.xds.service.impl;

import lombok.extern.slf4j.Slf4j;
import love.forte.simbot.api.message.results.GroupMemberList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.User;
import priv.xds.service.UserService;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuPeng
 * @date 2021-09-12 23:47
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(String qq, String groupCode) throws UnNecessaryInvokeException, NoRepeatableException {
        User user = userMapper.queryUser(qq, groupCode);
        if (user == null) {
            User newUser = new User();
            newUser.setQq(qq);
            newUser.setLastSign(new Date());
            newUser.setGroupCode(groupCode);
            userMapper.addUser(newUser);
            return;
        }
        // 查看是否被忽略
        if (user.isSignIgnore()) {
            throw new UnNecessaryInvokeException();
        }
        // 查看是否已经注册
        Instant ins = Instant.now();
        Instant lastSign = Instant.ofEpochMilli(user.getLastSign().getTime());
        long l = Duration.between(ins, lastSign).toDays();
        if (l == 0) {
            throw new NoRepeatableException("不能重复签到");
        }
        userMapper.sign(qq, groupCode);
    }

    @Override
    public List<User> getUnsignedUsers(String groupCode) {
        List<User> unsignedUserByGroup = userMapper.getUnsignedUserByGroup(groupCode);
        return unsignedUserByGroup.size() == 0 ? null : unsignedUserByGroup;
    }

    @Override
    public int getUserRole(String qq, String groupCode) {
        return userMapper.queryUser(qq, groupCode).getRole();
    }

    @Override
    public void ignoreUser(String qq, String groupCode) throws UnNecessaryInvokeException {
        int i = userMapper.ignoreUser(qq, groupCode);
        if (i == 0) {
            throw new UnNecessaryInvokeException("用户:" + qq + ",在群:" + groupCode + "已经被忽略");
        }
    }

    @Override
    public void reStatisticsUser(String qq, String groupCode) throws UnNecessaryInvokeException {
        int i = userMapper.reStatisticsUser(qq, groupCode);
        if (i == 0) {
            throw new UnNecessaryInvokeException("用户:" + qq + ",在群:" + groupCode + "没有被忽略");
        }
    }

    @Override
    public int initGroup(String groupCode, GroupMemberList groupMemberList) throws UnNecessaryInvokeException {
        List<User> allUsers = userMapper.getAllUsers(groupCode);
        List<String> collect = allUsers.stream().map(User::getQq).collect(Collectors.toList());

        List<User> unRegisteredUsers = groupMemberList.stream()
                .filter(groupMemberInfo -> !collect.contains(groupMemberInfo.getAccountCode()))
                .map(groupMemberInfo -> {
                    User user = new User();
                    user.setGroupCode(groupCode);
                    user.setQq(groupMemberInfo.getAccountCode());
                    user.setLastSign(new Date());
                    return user;
                })
                .collect(Collectors.toList());
        if (unRegisteredUsers.isEmpty()) {
            // 已经全部添加
            throw new UnNecessaryInvokeException();
        }
        userMapper.addUsers(unRegisteredUsers);
        return unRegisteredUsers.size();
    }
}
