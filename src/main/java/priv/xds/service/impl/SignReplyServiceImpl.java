package priv.xds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.xds.exception.MaxAllowedReplyCountException;
import priv.xds.mapper.SignReplyMapper;
import priv.xds.pojo.SignReply;
import priv.xds.service.SignReplyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2021-12-30 22:30
 */
@Service
public class SignReplyServiceImpl implements SignReplyService {

    private static final int MAX_REPLY_COUNT = 20;

    private static final Map<String, List<SignReply>> REPLY_CACHE = new HashMap<>(5);

    private SignReplyMapper signReplyMapper;

    @Autowired
    public void setSignReplyMapper(SignReplyMapper signReplyMapper) {
        this.signReplyMapper = signReplyMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addReply(String groupCode, String message) throws MaxAllowedReplyCountException {
        // 检查有没有超出上限
        List<SignReply> signReplies = REPLY_CACHE.get(groupCode);
        if (signReplies == null) {
            // 从数据库查
            signReplies = signReplyMapper.selectList(new QueryWrapper<SignReply>().eq(SignReplyMapper.COLUMN_GROUP_CODE, groupCode));
            int len = signReplies.size();
            if (len == 0) {
                // 空的, 直接添加
                recordReply(groupCode, message, signReplies);
            } else {
                // 有值
                if (len >= MAX_REPLY_COUNT) {
                    throw new MaxAllowedReplyCountException();
                } else {
                    // 添加
                    recordReply(groupCode, message, signReplies);
                }
            }
            REPLY_CACHE.put(groupCode, signReplies);
        } else {
            // 有缓存
            if (signReplies.size() >= MAX_REPLY_COUNT) {
                throw new MaxAllowedReplyCountException();
            } else {
                // 添加
                recordReply(groupCode, message, signReplies);
            }
        }
    }

    private void recordReply(String groupCode, String message, List<SignReply> signReplies) {
        SignReply signReply = new SignReply(groupCode, message);
        signReplies.add(signReply);
        signReplyMapper.insert(signReply);
    }


    @Override
    public SignReply getReply(String groupCode) {
        List<SignReply> signReplies = REPLY_CACHE.get(groupCode);
        if (signReplies == null) {
            // 从数据库查
            signReplies = signReplyMapper.selectList(new QueryWrapper<SignReply>().eq(SignReplyMapper.COLUMN_GROUP_CODE, groupCode));
            if (signReplies.isEmpty()) {
                return null;
            }
            // 放到缓存
            REPLY_CACHE.put(groupCode, signReplies);
        }
        return getRandomReply(signReplies);
    }

    private SignReply getRandomReply(List<SignReply> signReplies) {
        int maxIndex = signReplies.size() - 1;
        int random = (int) Math.round((Math.random() * maxIndex));
        return signReplies.get(random);
    }
}
