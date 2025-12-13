package io.github.yiwyn.deltabean.evnet;


/**
 * 字段比较器
 */
public interface FieldComparator {

    /**
     * 判断当前对比器是否支持该类型
     */
    boolean support(Class<?> fieldType);

    /**
     * 对比两个值是否相等
     */
    boolean equals(Object oldValue, Object newValue);

}
