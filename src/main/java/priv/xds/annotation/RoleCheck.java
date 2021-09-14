package priv.xds.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于检测用户权限
 * @author "DeSen Xu"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleCheck {
    /**
     * 用户权限等级
     * 1为默认等级
     * 2为管理员
     * 3为超级管理员
     */
    int role() default 1;
}
