package fun.lifepoem.tool.deltabean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: IgnoreDiff
 * @author: Yiwyn
 * @date: 2025/12/14 01:05
 * @Version: 1.0
 * 标记字段从候选列表中排除；实际字段过滤由比较模式执行。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface IgnoreDiff {

}
