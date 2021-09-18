package priv.xds.pojo;

import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-09-18 14:18
 */
@Data
public class StatisticGroup {
    /**
     * id
     */
    private int id;

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
