package priv.xds.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标注某个监听器是用于签到的
 * @author HuPeng
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SignGroup {
}
