package priv.xds.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author DeSen Xu
 * @date 2021-12-30 22:25
 */
@TableName("t_sign_reply")
@Data
public class SignReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("group_code")
    private String groupCode;

    @TableField("message")
    private String message;

    public SignReply(String groupCode, String message) {
        this.groupCode = groupCode;
        this.message = message;
    }

    public SignReply() {
    }
}
