package fun.lifepoem.tool.deltabean.impl.diffmode;

import cn.hutool.core.util.ReflectUtil;
import fun.lifepoem.tool.deltabean.DeltaBean;
import fun.lifepoem.tool.deltabean.entity.DiffItem;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffModeStrategy;
import fun.lifepoem.tool.deltabean.utils.CompleteEqualsUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @className: TemplateDiffModeStrategy
 * @author: Yiwyn
 * @date: 2025/12/14 01:27
 * @Version: 1.0
 * @description: 模版对比模式策略
 */
public class BeanTypeDiffModeStrategy implements BaseDiffModeStrategy {


    @Override
    public List<Field> diffFields(DeltaBean.Context context) {
        Object oldObject = context.getOldObject();
        Field[] fields = ReflectUtil.getFields(oldObject.getClass());
        return Arrays.asList(fields);
    }

    @Override
    public List<DiffItem> diff(DeltaBean.Context context) throws Exception {

        List<Field> diffFields = context.getTemplateFields();

        List<DiffItem> diffItems = context.getDiffItems();

        Object oldObj = context.getOldObject();
        Object newObj = context.getNewObject();

        for (Field tempField : diffFields) {

            tempField.setAccessible(true);

            // 从模版中获取对应对象的值
            Object oldValue = tempField.get(oldObj);
            Object newValue = tempField.get(newObj);

            // 比较两个值
            boolean fieldEquals = CompleteEqualsUtil.equals(oldValue, newValue);

            if (!fieldEquals) {
                diffItems.add(new DiffItem(tempField, oldValue, newValue));
            }
        }

        return diffItems;
    }


}
