package priv.xds.exception;

/**
 * 不可重复的错误
 * 一般用于表示用户进行了重复的操作
 * 例如重复签到
 * @author HuPeng
 * @date 2021-09-12 23:45
 */
public class NoRepeatableException extends Exception{

    public NoRepeatableException(){}

    public NoRepeatableException(String message) {
        super(message);
    }
}
