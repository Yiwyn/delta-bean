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

    List<Field> diffFields(DeltaBean.Context context);


    List<DiffItem> diff(DeltaBean.Context context) throws Exception;
}
