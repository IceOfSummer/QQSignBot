package priv.xds.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author HuPeng
 * @date 2021-09-12 23:22
 */
@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户qq号
     */
    @TableField("qq")
    private String qq;

    /**
     * 上次打卡时间
     */
    @TableField("last_sign")
    private Date lastSign;

    /**
     * 群号
     */
    @TableField("group_code")
    private String groupCode;

    /**
     * 权限等级
     */
    @TableField("role")
    private Integer role;

    /**
     * 是否被忽略
     */
    @TableField("sign_ignore")
    private boolean signIgnore;
}
