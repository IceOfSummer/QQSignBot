package priv.xds.pojo;

import lombok.Data;

/**
 * @author DeSen Xu
 * @date 2021-09-18 14:16
 */
@Data
public class Statistic {
    /**
     * 当前统计的id
     */
    private int id;

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
