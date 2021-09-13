package priv.xds.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author HuPeng
 * @date 2021-09-12 23:22
 */
@Data
public class User {
    /**
     * 用户qq号
     */
    private String qq;

    /**
     * 上次打卡时间
     */
    private Date lastSign;

    /**
     * 连续打卡时间
     */
    private int consecutiveSignDays;

    /**
     * 群号
     */
    private String groupCode;

    /**
     * 权限等级
     */
    private int role;
}
