package io.github.yiwyn.deltabean.entity;

/**
 * @className: DiffItem
 * @author: Yiwyn
 * @date: 2025/12/14 00:06
 * @Version: 1.0
 * @description: 差异项目
 */
public class DiffItem {

    /**
     * 新值
     */
    private final Object newValue;

    /**
     * 旧值
     */
    private final Object oldValue;


    public DiffItem(Object oldValue, Object newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }


    @Override
    public String toString() {
        return "DiffItem{" +
                "newValue=" + newValue +
                ", oldValue=" + oldValue +
                '}';
    }
}
