package priv.xds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import priv.xds.exception.MaxAllowedReplyCountException;
import priv.xds.pojo.SignReply;

/**
 * @author DeSen Xu
 * @date 2021-12-30 22:26
 */
@Mapper
public interface SignReplyMapper extends BaseMapper<SignReply> {

    String COLUMN_GROUP_CODE = "group_code";
}
