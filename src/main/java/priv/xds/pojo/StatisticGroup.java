package priv.xds.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-09-18 14:18
 */
@Data
@TableName("t_statistic_group")
public class StatisticGroup {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 群号
     */
    private String groupCode;

    /**
     * 这次统计的名称
     */
    private String name;

    /**
     * 是否被激活了
     */
    private Boolean active;
}
