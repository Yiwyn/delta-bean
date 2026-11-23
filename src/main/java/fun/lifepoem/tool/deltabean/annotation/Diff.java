package fun.lifepoem.tool.deltabean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: Diff
 * @author: Yiwyn
 * @date: 2025/12/13 23:21
 * @Version: 1.0
 * 配置差异字段 ID、描述和转换事件；运行时可见，可用于字段或类型。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Diff {

    /**
     * 差异字段 ID；为空时保留字段名。
     */
    String diffFieldId() default "";

    /**
     * 差异字段描述；为空时保留字段名。
     */
    String diffFieldDesc() default "";

    /**
     * 转换事件 ID；为空时由执行器使用字段名。
     */
    String onDiffEventId() default "";
}
