package priv.xds.pojo;

import lombok.Data;
import java.util.Objects;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:23
 */
@Data
public class AutoSign {

    /**
     * 在提交的时候要交付的数据个数
     */
    public static final int KEY_WORD_COUNT = 6;

    /**
     * qq号
     */
    private String qq;

    /**
     * token信息
     */
    private String token;

    /**
     * 是否开启自动打卡
     */
    private Boolean active;

    /**
     * 学号
     */
    private String yhm;

    /**
     * 学生电话
     */
    private String lxdh;

    /**
     * 学生家长名称
     */
    private String jjlxr;

    /**
     * 家长联系电话
     */
    private String jjlxdh;

    /**
     * 群组
     */
    private String groupCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoSign autoSign = (AutoSign) o;
        return qq.equals(autoSign.qq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qq);
    }

    @Override
    public String toString() {
        return "当前保存的用户信息:" +
                "\n是否激活自动打卡: " + active +
                "\n学号: " + yhm +
                "\n联系电话: " + lxdh +
                "\n家长姓名: " + jjlxr +
                "\n家长联系电话: " + jjlxdh;
    }
}
