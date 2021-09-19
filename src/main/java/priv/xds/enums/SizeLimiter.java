package priv.xds.enums;

/**
 * 用于限制字段长度
 * @author DeSen Xu
 * @date 2021-09-18 15:44
 */
public enum SizeLimiter {
    /**
     * 创建统计时,统计名称的最大长度
     */
    STATISTIC_GROUP_NAME(30),
    /**
     * 提交统计时的内容长度
     */
    STATISTIC_CONTENT(500),
    /**
     * 当要把字符串转换为数字时,使用该值限制字符串长度
     */
    NUMBER(10),
    /**
     * QQ号
     */
    QQ(20),
    /**
     * token的长度
     */
    TOKEN(250),
    /**
     * 自动打卡所用数据的长度
     */
    AUTO_SIGN_COMMON(13);


    private final int maxLength;

    SizeLimiter(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    /**
     * 查看当前值是否非法
     * @param val 要检查的值
     * @return 返回true表示不合法
     */
    public boolean isIllegal(int val) {
        return val >= maxLength;
    }
}
