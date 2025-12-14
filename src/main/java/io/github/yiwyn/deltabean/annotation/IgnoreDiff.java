package io.github.yiwyn.deltabean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: IgnoreDiff
 * @author: Yiwyn
 * @date: 2025/12/14 01:05
 * @Version: 1.0
 * @description: 忽略对比
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface IgnoreDiff {

}
