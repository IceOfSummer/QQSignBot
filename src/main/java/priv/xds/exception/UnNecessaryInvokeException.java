package priv.xds.exception;

/**
 * 不必要的操作
 * 通常用于表示某个数据已经是当前操作想要调整的状态了
 * @author "DeSen Xu"
 * @date 2021-09-14 22:31
 */
public class UnNecessaryInvokeException extends Exception{

    public UnNecessaryInvokeException(){}

    public UnNecessaryInvokeException(String message) {
        super(message);
    }
}
