package priv.xds.exception;

/**
 * @author DeSen Xu
 * @date 2021-09-19 11:27
 */
public class FailToExecuteException extends Exception{

    public FailToExecuteException() {
        super();
    }

    public FailToExecuteException(String message) {
        super(message);
    }
}
