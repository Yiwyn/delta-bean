package io.github.yiwyn.deltabean.entity;

import java.lang.reflect.Field;

/**
 * @className: DiffItem
 * @author: Yiwyn
 * @date: 2025/12/14 00:06
 * @Version: 1.0
 * @description: 差异项目
 */
public class DiffItem {

    /**
     * 对比字段
     */
    private final Field field;
    /**
     * 新值
     */
    private final Object newValue;
    /**
     * 旧值
     */
    private final Object oldValue;

    /**
     * 旧值转移
     */
    private Object oldValueTrans;

    /**
     * 新值转移
     */
    private Object newValueTrans;


    public void setOldValueTrans(Object oldValueTrans) {
        this.oldValueTrans = oldValueTrans;
    }

    public void setNewValueTrans(Object newValueTrans) {
        this.newValueTrans = newValueTrans;
    }

    /**
     * 字段名
     */
    private String fieldName;

    public DiffItem(Field field, Object oldValue, Object newValue) {
        this.field = field;
        this.fieldName = field.getName();
        this.oldValue = oldValue;
        this.newValue = newValue;
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Field getField() {
        return field;
    }

    public Object getNewValue() {
        return newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "DiffItem{" + "field=" + field + ", newValue=" + newValue + ", oldValue=" + oldValue + ", oldValueTrans=" + oldValueTrans + ", newValueTrans=" + newValueTrans + ", fieldName='" + fieldName + '\'' + '}';
    }
}
