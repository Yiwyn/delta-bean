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
 * @description: 模版模式策略
 */
public interface BaseDiffModeStrategy {

    /**
     * 获取需要对比的字段集合
     *
     * @param context 差异对比上文件信息
     */
    List<Field> diffFields(DeltaBean.Context context);


    /**
     * 变更对比
     *
     * @param context 差异对比上文件信息
     * @return 变更的项目集合
     */
    List<DiffItem> diff(DeltaBean.Context context) throws Exception;
}
