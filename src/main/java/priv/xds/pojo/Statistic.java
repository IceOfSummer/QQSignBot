package priv.xds.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-09-18 14:16
 */
@Data
@TableName("t_statistic")
public class Statistic {
    /**
     * 当前统计的id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 统计的内容
     */
    private String content;

    /**
     * 发送者的qq
     */
    private String qq;

    /**
     * 对应的统计记录
     */
    private int statisticGroupId;

}
