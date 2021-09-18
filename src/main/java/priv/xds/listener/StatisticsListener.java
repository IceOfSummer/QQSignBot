package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.annotation.RoleCheck;
import priv.xds.enums.SizeLimiter;
import priv.xds.exception.NoRepeatableException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.Statistic;
import priv.xds.pojo.StatisticGroup;
import priv.xds.service.StatisticGroupService;
import priv.xds.service.StatisticService;
import priv.xds.util.MessageUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用于统计字段
 * @author DeSen Xu
 * @date 2021-09-18 13:32
 */
@Controller
public class StatisticsListener {

    private StatisticGroupService statisticGroupService;

    private StatisticService statisticService;

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Autowired
    public void setStatisticGroupService(StatisticGroupService statisticGroupService) {
        this.statisticGroupService = statisticGroupService;
    }

    @OnGroup
    @Filter(value = "^收集 \\S+", matchType = MatchType.REGEX_MATCHES)
    @RoleCheck(2)
    public void launchStatistic(GroupMsg groupMsg, MsgSender sender) {
        String name = groupMsg.getText().split(" ")[1];
        int length = name.length();
        if (SizeLimiter.STATISTIC_GROUP_NAME.isIllegal(length)) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "输入的名称太长, 最大长度为:" + SizeLimiter.STATISTIC_GROUP_NAME.getMaxLength() + "而输入的长度为: " + length);
            return;
        }
        try {
            int id = statisticGroupService.launchStatistic(groupMsg.getGroupInfo().getGroupCode(), name);
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "创建成功, 该统计id为: " + id);
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + e.getMessage());
        }
    }

    @OnGroup
    @Filter(value = "^停止收集 \\S+", matchType = MatchType.REGEX_MATCHES)
    @RoleCheck(2)
    public void stopStatistic(GroupMsg groupMsg, MsgSender sender) {
        int id = getId(groupMsg);
        if (id == 0) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "id有误,请检查后输入");
            return;
        }
        try {
            statisticGroupService.stopStatistic(id, groupMsg.getGroupInfo().getGroupCode());
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "成功停止统计id为" + id + "的统计");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "无法找到该id或者该统计已经是停止状态");
        }
    }

    @OnGroup
    @Filter(value = "^获取收集结果 \\S+", matchType = MatchType.REGEX_MATCHES)
    @RoleCheck(2)
    public void getStatisticStatus(GroupMsg groupMsg, MsgSender sender) {
        int id = getId(groupMsg);
        if (id == 0) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "id有误,请检查后输入");
            return;
        }
        try {
            List<Statistic> statisticInfo = statisticGroupService.getStatisticInfo(id, groupMsg.getGroupInfo().getGroupCode());
            StringBuilder builder = new StringBuilder(statisticInfo.size() * 20);
            builder.append("\n");
            for (Statistic statistic : statisticInfo) {
                GroupMemberInfo memberInfo = sender.GETTER.getMemberInfo(groupMsg.getGroupInfo().getGroupCode(), statistic.getQq());
                builder
                        .append(MessageUtil.combineQqAndNickname(memberInfo))
                        .append(": ")
                        .append(statistic.getContent())
                        .append("\n");
            }
            builder.deleteCharAt(builder.length() - 1);
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + builder);
        } catch (NoTargetValueException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "无法找到该id");
        }
    }

    @OnGroup
    @Filter(value = "提交收集", matchType = MatchType.STARTS_WITH)
    public void submitStatistic(GroupMsg groupMsg, MsgSender sender) {
        String msg = groupMsg.getMsg().trim().replace("提交收集", "");
        if (SizeLimiter.STATISTIC_CONTENT.isIllegal(msg.length())) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "输入的内容太长了!");
            return;
        } else if (msg.length() == 0) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "请附带收集内容" + MessageUtil.sendImageByFile("classpath:images/mad.gif"));
            return;
        }
        try {
            statisticService.submitContent(msg, groupMsg.getAccountInfo().getAccountCode(), groupMsg.getGroupInfo().getGroupCode());
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "提交成功!");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "当前没有激活的统计!");
        } catch (NoRepeatableException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "不允许重复提交!");
        }
    }

    @OnGroup
    @Filter(value = "查看收集表", matchType = MatchType.EQUALS)
    public void getAllStatistic(GroupMsg groupMsg, MsgSender sender) {
        List<StatisticGroup> allStatistic = statisticGroupService.getAllStatistic(groupMsg.getGroupInfo().getGroupCode());
        StringBuilder builder = new StringBuilder(allStatistic.size() * 20);
        builder.append("\n");
        for (StatisticGroup statisticGroup : allStatistic) {
            if (statisticGroup.getActive()) {
                builder.append("*");
            }
            builder
                    .append("统计名称: ")
                    .append(statisticGroup.getName())
                    .append(" ; id: ")
                    .append(statisticGroup.getId());
            if (statisticGroup.getActive()) {
                builder
                        .append("*");
            }
            builder.append("\n");
        }
        builder.append("注: 左右带*号的代表当前正在收集的统计");
        // 删除多余换行
        builder.deleteCharAt(builder.length() - 1);
        sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) +  builder);
    }

    @OnGroup
    @Filter(value = "^查看收集未提交 \\S+", matchType = MatchType.REGEX_MATCHES)
    @RoleCheck(2)
    public void tipUnSubmit(GroupMsg groupMsg, MsgSender sender) {
        int id = getId(groupMsg);
        if (id == 0) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "输入的id有误");
            return;
        }
        try {
            Set<String> collect = statisticGroupService.getStatisticInfo(id, groupMsg.getGroupInfo().getGroupCode()).stream().map(Statistic::getQq).collect(Collectors.toSet());
            StringBuilder builder = new StringBuilder(50);
            builder.append("\n");
            for (GroupMemberInfo groupMemberInfo : sender.GETTER.getGroupMemberList(groupMsg).stream().filter(groupMemberInfo -> !collect.contains(groupMemberInfo.getAccountCode())).collect(Collectors.toList())) {
                builder.append(MessageUtil.combineQqAndNickname(groupMemberInfo))
                        .append("\n");
            }
            // 删除多余换行
            builder.deleteCharAt(builder.length() - 1);
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + builder);
        } catch (NoTargetValueException e) {
            sender.SENDER.sendGroupMsg(groupMsg, MessageUtil.atSomeone(groupMsg) + "没有找到该统计!");
        }
    }

    /**
     * 从指令中分离id
     * @param groupMsg groupMsg
     * @return 若返回0,表示传入的值不合法,其余值代表正常的转换后的id
     */
    private int getId(GroupMsg groupMsg) {
        String str = groupMsg.getText().split(" ")[1];
        int len = str.length();
        if (SizeLimiter.NUMBER.isIllegal(len)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
