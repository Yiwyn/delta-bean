package fun.lifepoem.tool.deltabean.interfaceable;

import fun.lifepoem.tool.deltabean.DeltaBean;
import fun.lifepoem.tool.deltabean.entity.DiffItem;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @className: BaseDiffModeStrategy
 * @author: Yiwyn
 * @date: 2025/12/14 01:20
 * @Version: 1.0
 * 比较策略抽象。字段列表由模式决定，{@link #diff(DeltaBean.Context)} 在上下文中写入差异。
 */
public interface BaseDiffModeStrategy {

    /**
     * 获取需要对比的字段集合
     *
     * @param context 包含比较类、模式及对象的上下文
     * @return 待比较字段
     */
    List<Field> diffFields(DeltaBean.Context context);


    /**
     * 变更对比
     *
     * @param context 必须已设置待比较字段的上下文
     * @return 变更项目集合
     * @throws Exception 反射或策略执行失败
     */
    List<DiffItem> diff(DeltaBean.Context context) throws Exception;
}
