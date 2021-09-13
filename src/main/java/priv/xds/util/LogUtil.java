package priv.xds.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author HuPeng
 * @date 2021-09-13 11:30
 */
@Deprecated
public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static Logger getLogger() {
        return logger;
    }
}
