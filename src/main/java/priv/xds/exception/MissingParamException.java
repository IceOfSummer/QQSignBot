package priv.xds.exception;

/**
 * 用于表示参数中缺少某些参数
 * @author "DeSen Xu"
 * @date 2021-09-14 12:42
 */
public class MissingParamException extends Exception{

    public MissingParamException(){}

    public MissingParamException(String message) {
        super(message);
    }
}
