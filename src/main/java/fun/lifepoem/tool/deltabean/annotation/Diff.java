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
 * @description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Diff {

    /**
     * 变更字段名
     */
    String diffFieldId() default "";


    /**
     * 变更字段描述
     */
    String diffFieldDesc() default "";

    /**
     * 变更字段事件id,逻辑处理中若为空串则默认使用fieldId作为事件id
     */
    String onDiffEventId() default "";
}
