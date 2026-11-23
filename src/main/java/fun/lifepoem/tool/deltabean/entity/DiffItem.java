package fun.lifepoem.tool.deltabean.entity;

import java.lang.reflect.Field;

/**
 * @className: DiffItem
 * @author: Yiwyn
 * @date: 2025/12/14 00:06
 * @Version: 1.0
 * 单个字段的差异，保存原始值及可选的转换值。
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
    private String fieldId;

    /**
     * 字段描述
     */
    private String fieldDesc;


    /** 创建差异项目；无转换事件时转换值等于原始值。 */
    public DiffItem(Field field, Object oldValue, Object newValue) {
        this.field = field;
        this.fieldId = field.getName();
        this.fieldDesc = field.getName();
        this.oldValue = oldValue;
        this.oldValueTrans = oldValue;
        this.newValue = newValue;
        this.newValueTrans = newValue;
    }


    /** @return 反射字段 */
    public Field getField() {
        return field;
    }

    /** @return 新对象原始值 */
    public Object getNewValue() {
        return newValue;
    }

    /** @return 旧对象原始值 */
    public Object getOldValue() {
        return oldValue;
    }

    public Object getOldValueTrans() {
        return oldValueTrans;
    }

    public Object getNewValueTrans() {
        return newValueTrans;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    @Override
    public String toString() {
        return "DiffItem{" +
                "field=" + field +
                ", newValue=" + newValue +
                ", oldValue=" + oldValue +
                ", oldValueTrans=" + oldValueTrans +
                ", newValueTrans=" + newValueTrans +
                ", fieldId='" + fieldId + '\'' +
                ", fieldDesc='" + fieldDesc + '\'' +
                '}';
    }
}

