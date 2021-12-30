package priv.xds.service;

import org.springframework.lang.Nullable;
import priv.xds.exception.MaxAllowedReplyCountException;
import priv.xds.pojo.SignReply;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2021-12-30 22:26
 */
public interface SignReplyService {

    /**
     * 添加一条自定义回复
     * @param groupCode 群号
     * @param message 自定义回复消息
     * @throws MaxAllowedReplyCountException 到达了最大允许的条数
     */
    void addReply(String groupCode, String message) throws MaxAllowedReplyCountException;

    /**
     * 获取自定义回复信息
     * @param groupCode 群号
     * @return 自定义回复信息,返回null表示该群目前没有自定义回复信息
     */
    @Nullable
    SignReply getReply(String groupCode);
}
