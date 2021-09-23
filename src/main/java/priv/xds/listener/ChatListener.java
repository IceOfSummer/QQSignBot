package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


/**
 * 用于平常聊天
 * @author HuPeng
 * @date 2021-09-13 22:34
 */
@Controller
public class ChatListener {

    @Value("${app.version}")
    private String version;


    public static final String HELP = "===打卡统计功能===\n" +
            "- 打卡: 群内用户输入打卡完成每日打卡功能\n" +
            "- 忽略 *QQ号* : 仅群内管理员或群主可用，可以让机器人不再统计该用户的打卡情况\n" +
            "- 取消忽略 *QQ号* :  仅群内管理员或群主可用，可以让机器人重新统计该用户的打卡情况\n" +
            "- 提醒未打卡: 仅群内管理员或群主可用，让机器人at所有没有打卡的人\n" +
            "- 打卡情况: 所有人可用，显示所有没有打卡的人(不会at)\n" +
            "- 代打卡 *QQ号* : 群内管理员或群主手动为某人打卡，一般用于服务器出现bug重启后的补救措施 \n" +
            "===收集功能===\n" +
            "- 收集 *收集名称* : 开启一个收集任务,在执行成功后会给出一个收集id，后续停止或者查看收集情况都需要该id。同一时间内只能有一个激活的收集！\n" +
            "- 停止收集 *收集id* : 停止某一个收集\n" +
            "- 获取收集结果 *收集id* : 获取某一个收集的收集结果\n" +
            "- 提交收集 *任意内容* : 群内成员根据情况，提交相关内容到激活的收集中，内容可以是任意的，可以带有空格，但最长为100个字符\n" +
            "- 查看收集表: 查看群内所有开启过的收集表，被激活的收集会被标注\n" +
            "- 查看收集未提交 *收集id* : 查看群内所有没有提交收集的人\n" +
            "更多帮助请查看: https://github.com/HuPeng333/QQSignBot";

    @OnGroup
    @Priority(PriorityConstant.LAST)
    @Filter(value = "^\\s*", atBot = true, matchType = MatchType.REGEX_MATCHES)
    public void replyAt(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, "at我发送'帮助'获取更多支持!\ngithub地址:https://github.com/HuPeng333/QQSignBot\n当前版本: " + version);
    }

    @OnGroup
    @Filter(atBot = true, value = "帮助", matchType = MatchType.CONTAINS)
    public void help(GroupMsg groupMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(groupMsg, HELP);
    }

}
