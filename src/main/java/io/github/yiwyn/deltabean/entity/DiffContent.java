package io.github.yiwyn.deltabean.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: DiffContent
 * @author: Yiwyn
 * @date: 2025/12/13 23:36
 * @Version: 1.0
 * @description: 差异信息
 */
public class DiffContent {

    /**
     * 类名
     */
    private final String className;

    /**
     * 元类
     */
    private final Class<?> diffClass;

    /**
     * 变更项目
     */
    private List<DiffItem> diffItems;


    public DiffContent(Class<?> diffClass) {
        this.diffClass = diffClass;
        this.className = diffClass.getSimpleName();
    }

    public List<DiffItem> getDiffItems() {
        return diffItems;
    }

    public void setDiffItems(List<DiffItem> diffItems) {
        this.diffItems = diffItems;
    }

    @Override
    public String toString() {
        return "DiffContent{" +
                "className='" + className + '\'' +
                ", diffClass=" + diffClass +
                ", diffItems=" + diffItems +
                '}';
    }
}
