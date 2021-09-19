package priv.xds.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import priv.xds.enums.SizeLimiter;
import priv.xds.exception.FailToExecuteException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.pojo.AutoSign;
import priv.xds.service.AutoSignService;

import static priv.xds.pojo.AutoSign.KEY_WORD_COUNT;

/**
 * 自动打卡
 * @author DeSen Xu
 * @date 2021-09-19 10:52
 */
@Controller
public class AutoSignListener {

    public final String HELP = "格式:注册自动打卡 token 学号 电话 家长姓名 家长联系电话";

    private AutoSignService autoSignService;

    @Autowired
    public void setAutoSignService(AutoSignService autoSignService) {
        this.autoSignService = autoSignService;
    }

    @OnPrivate
    @Filter(value = "注册自动打卡", matchType = MatchType.STARTS_WITH)
    public void resistAutoSign(PrivateMsg privateMsg, MsgSender sender) {
        String[] param = privateMsg.getText().split(" ");
        if (param.length != KEY_WORD_COUNT) {
            sender.SENDER.sendPrivateMsg(privateMsg, "注册失败" + HELP);
            return;
        }
        String token = param[1];
        String yhm = param[2];
        String lxdh = param[3];
        String jjlxr = param[4];
        String jjlxdh = param[5];
        // 表单验证
        for (int i = 2; i < param.length; i++) {
            if (SizeLimiter.AUTO_SIGN_COMMON.isIllegal(param[i].length())) {
                sender.SENDER.sendPrivateMsg(privateMsg, "注册失败, 请检查你的输入是否过长");
                return;
            }
        }
        // token
        if (SizeLimiter.TOKEN.isIllegal(token.length())) {
            sender.SENDER.sendPrivateMsg(privateMsg, "注册失败, 请检查你的输入是否过长");
        }
        AutoSign autoSign = new AutoSign();
        autoSign.setToken(token);
        autoSign.setJjlxdh(jjlxdh);
        autoSign.setYhm(yhm);
        autoSign.setLxdh(lxdh);
        autoSign.setJjlxr(jjlxr);
        autoSign.setQq(privateMsg.getAccountInfo().getAccountCode());
        autoSignService.registerAutoSign(autoSign);
        sender.SENDER.sendPrivateMsg(privateMsg, "注册成功,请检查你的信息:" +
                "\ntoken: " + token +
                "\n学号: " + yhm +
                "\n电话: " + lxdh +
                "\n家长姓名: " + jjlxr +
                "\n家长联系电话: " + jjlxdh +
                "\n若检查无误后,输出'开启自动打卡'即可自动为你打卡" +
                "\n若输入有误,重新执行该指令即可" +
                "\n在开启自动打卡之前希望你已经充分阅读过泄露Token的后果" +
                "\n链接: ");
    }

    @OnPrivate
    @Filter(value = "修改token", matchType = MatchType.STARTS_WITH)
    public void modifyToken(PrivateMsg privateMsg, MsgSender sender) {
        String token = privateMsg.getText().split(" ")[1];
        if (SizeLimiter.TOKEN.isIllegal(token.length())) {
            sender.SENDER.sendPrivateMsg(privateMsg, "token无效,请检查你的输入");
            return;
        }
        try {
            autoSignService.modifyToken(privateMsg.getAccountInfo().getAccountCode(), token);
            sender.SENDER.sendPrivateMsg(privateMsg, "修改成功");
        } catch (NoTargetValueException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "你还没有注册,或者修改了相同的token\n如果你想要注册\n" + HELP);
        }
    }

    @OnPrivate
    @Filter(value = "手动打卡", matchType = MatchType.EQUALS)
    public void handSign(PrivateMsg privateMsg, MsgSender sender) {
        try {
            autoSignService.sign(privateMsg.getAccountInfo().getAccountCode());
            sender.SENDER.sendPrivateMsg(privateMsg, "打卡成功!");
        } catch (NoTargetValueException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "你还没有注册\n如果你想要注册,请输入:\n" + HELP);
        } catch (FailToExecuteException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "打卡失败: " + e);
        }
    }

    @OnPrivate
    @Filter(value = "开启自动打卡", matchType = MatchType.EQUALS)
    public void launchAutoSign(PrivateMsg privateMsg, MsgSender sender) {
        try {
            autoSignService.changeAutoSignStatus(privateMsg.getAccountInfo().getAccountCode(), true);
            sender.SENDER.sendPrivateMsg(privateMsg, "开启成功!\n请注意,开启后不要在QQ群内发生'打卡',否则自动打卡会失效!");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "已经是开启状态或者你还没有注册\n" + HELP);
        }
    }


    @OnPrivate
    @Filter(value = "关闭自动打卡", matchType = MatchType.EQUALS)
    public void stopAutoSign(PrivateMsg privateMsg, MsgSender sender) {
        try {
            autoSignService.changeAutoSignStatus(privateMsg.getAccountInfo().getAccountCode(), false);
            sender.SENDER.sendPrivateMsg(privateMsg, "关闭成功!");
        } catch (UnNecessaryInvokeException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "已经是关闭状态或者你还没有注册\n" + HELP);
        }
    }

    @OnPrivate
    @Filter(value = "查看提交信息", matchType = MatchType.EQUALS)
    public void getInfo(PrivateMsg privateMsg, MsgSender sender) {
        try {
            AutoSign info = autoSignService.getInfo(privateMsg.getAccountInfo().getAccountCode());
            sender.SENDER.sendPrivateMsg(privateMsg, info.toString());
        } catch (NoTargetValueException e) {
            sender.SENDER.sendPrivateMsg(privateMsg, "你还没有注册,请先注册\n" + HELP);
        }
    }
}
