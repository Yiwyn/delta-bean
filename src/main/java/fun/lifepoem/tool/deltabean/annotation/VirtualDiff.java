package fun.lifepoem.tool.deltabean.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 抽象字段
 * 如果比较对象中的内容需要先加工，然后再对比可以使用这个注解进行标识
 * 标识的字段需要搭配返回类型
 * @see fun.lifepoem.tool.deltabean.interfaceable.event.VirtualField
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface VirtualDiff {
}
