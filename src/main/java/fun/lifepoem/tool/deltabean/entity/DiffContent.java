package fun.lifepoem.tool.deltabean.entity;

import java.util.List;

/**
 * @className: DiffContent
 * @author: Yiwyn
 * @date: 2025/12/13 23:36
 * @Version: 1.0
 * 一次比较的结果，包含比较类名称和差异项目。
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
    private final List<DiffItem> diffItems;


    /** @param diffClass 比较对象类；@param diffItems 差异项目 */
    public DiffContent(Class<?> diffClass, List<DiffItem> diffItems) {
        this.diffClass = diffClass;
        this.className = diffClass.getSimpleName();
        this.diffItems = diffItems;
    }

    /** @return 差异项目列表 */
    public List<DiffItem> getDiffItems() {
        return diffItems;
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
