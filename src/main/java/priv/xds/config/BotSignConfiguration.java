package priv.xds.config;

import love.forte.simbot.api.message.containers.AccountCodeContainer;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.bot.BotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import priv.xds.aop.SignFilter;
import priv.xds.mapper.GroupMapper;
import priv.xds.mapper.UserMapper;
import priv.xds.pojo.Group;
import priv.xds.pojo.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置哪些群组应该用于签到
 * @see priv.xds.aop.SignFilter SignFilter 使用aop过滤监听
 * @author HuPeng
 * @date 2021-09-13 10:10
 */
@Configuration
@EnableConfigurationProperties(BotSignProperties.class)
public class BotSignConfiguration {

    private BotSignProperties botSignProperties;

    private GroupMapper groupMapper;

    private BotManager botManager;

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setBotManager(BotManager botManager) {
        this.botManager = botManager;
    }

    @Autowired
    public void setBotSignProperties(BotSignProperties botSignProperties) {
        this.botSignProperties = botSignProperties;
    }

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    /**
     * 检查所有没有注册的群
     * 没有注册的群指在配置文件中声明了某个群，但是在数据库没有相关数据的
     */
    @Bean
    @Transactional(rollbackFor = Exception.class)
    public void checkUnregisteredGroup() {
        Logger logger = LoggerFactory.getLogger(SignFilter.class);
        logger.info("正在搜索是否有未注册的群组");
        List<Group> allRegisteredGroup = groupMapper.getAllRegisteredGroup();
        List<String> targetGroup = botSignProperties.getTargetGroup();

        if (allRegisteredGroup.isEmpty()) {
            for (String groupCode : targetGroup) {
                logger.info("正在注册QQ群: " + groupCode);
                registerGroup(groupCode);
                logger.info("注册完毕");
            }
            return;
        }

        // 后期添加新群组
        for (Group group : allRegisteredGroup) {
            String groupCode = group.getGroupCode();
            if (!targetGroup.contains(groupCode)) {
                logger.info("正在注册QQ群: " + groupCode);
                registerGroup(groupCode);
                logger.info("注册完毕");
            }
        }
    }

    private void registerGroup(String groupCode) {
        List<User> allUsers = userMapper.getAllUsers(groupCode);
        // 该群组没有注册
        List<GroupMemberInfo> list = botManager.getDefaultBot().getSender().GETTER.getGroupMemberList(groupCode)
                .stream()
                .filter(groupMemberInfo -> allUsers.stream().noneMatch(user -> user.getQq().equals(groupMemberInfo.getAccountCode())))
                .sorted(Comparator.comparing(AccountCodeContainer::getAccountCode)).collect(Collectors.toList());
        List<User> users = new ArrayList<>();
        for (GroupMemberInfo groupMemberInfo : list) {
            User user = new User();
            user.setQq(groupMemberInfo.getAccountCode());
            user.setLastSign(new Date());
            user.setGroupCode(groupCode);
            users.add(user);
        }
        userMapper.addUsers(users);
        groupMapper.addGroup(groupCode);
    }

}
