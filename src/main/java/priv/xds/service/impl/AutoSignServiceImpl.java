package priv.xds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.xds.exception.FailToExecuteException;
import priv.xds.exception.NoTargetValueException;
import priv.xds.exception.UnNecessaryInvokeException;
import priv.xds.function.SignHelper;
import priv.xds.mapper.AutoSignMapper;
import priv.xds.pojo.AutoSign;
import priv.xds.service.AutoSignService;
import priv.xds.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:24
 */
@Service
public class AutoSignServiceImpl implements AutoSignService {

    private AutoSignMapper autoSignMapper;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAutoSignMapper(AutoSignMapper autoSignMapper) {
        this.autoSignMapper = autoSignMapper;
    }

    @Override
    public void registerAutoSign(AutoSign autoSign) {
        autoSignMapper.resistAutoSign(autoSign);
    }

    @Override
    public void changeAutoSignStatus(String qq, boolean active) throws UnNecessaryInvokeException {
        int i = autoSignMapper.updateLaunchStatus(qq, active);
        if (i == 0) {
            throw new UnNecessaryInvokeException();
        }
    }

    @Override
    public void modifyToken(String qq, String token) throws NoTargetValueException {
        AutoSign autoSign = new AutoSign();
        autoSign.setQq(qq);
        autoSign.setToken(token);
        int i = autoSignMapper.updateAutoSign(autoSign);
        if (i == 0) {
            throw new NoTargetValueException();
        }
    }

    @Override
    public void sign(String qq) throws NoTargetValueException, FailToExecuteException {
        AutoSign user = autoSignMapper.getUser(qq);
        if (user == null) {
            throw new NoTargetValueException();
        }
        // 打卡
        try {
            SignHelper.Resp send = new SignHelper(user).send();
            String successCode = "0x00000000";
            if (!successCode.equals(send.getErrorCode())) {
                // 打卡失败
                throw new FailToExecuteException(send.getMessage());
            }
            // 打卡成功
            userService.sign(qq);
        } catch (IOException e) {
            throw new FailToExecuteException(e.getMessage());
        }
    }

    @Override
    public String sign(AutoSign autoSign) throws IOException, FailToExecuteException{
        SignHelper.Resp send = new SignHelper(autoSign).send();
        String successCode = "0x00000000";
        if (successCode.equals(send.getErrorCode())) {
            // success
            userService.sign(autoSign.getQq());
            return "已经帮你自动打卡了!";
        } else {
            throw new FailToExecuteException("自动打卡失败: " + send.getMessage());
        }
    }

    @Override
    public List<AutoSign> getUnSignedUser() {
        return autoSignMapper.getUnSignedUser();
    }

    @Override
    public AutoSign getInfo(String qq) throws NoTargetValueException {
        AutoSign user = autoSignMapper.getUser(qq);
        if (user == null) {
            throw new NoTargetValueException();
        }
        user.setToken(null);
        return user;
    }
}
