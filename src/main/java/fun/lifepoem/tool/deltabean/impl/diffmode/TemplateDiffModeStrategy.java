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
public class TemplateDiffModeStrategy implements BaseDiffModeStrategy {


    @Override
    public List<Field> diffFields(DeltaBean.Context context) {
        Field[] fields = ReflectUtil.getFields(context.getDiffTmplBean().getClass());
        return Arrays.asList(fields);
    }

    @Override
    public List<DiffItem> diff(DeltaBean.Context context) throws Exception {

        List<Field> diffFields = context.getTemplateFields();

        List<DiffItem> diffItems = context.getDiffItems();

        Object oldObj = context.getOldObject();
        Object newObj = context.getNewObject();


        for (Field tempField : diffFields) {

            String fieldName = tempField.getName();

            // 从模版中获取对应对象的值
            Field field = ReflectUtil.getField(oldObj.getClass(), fieldName);
            if (field == null) {
                continue;
            }

            field.setAccessible(true);

            Object oldValue = field.get(oldObj);
            Object newValue = field.get(newObj);

            // 获取模版参数类型
            boolean fieldEquals = CompleteEqualsUtil.equals(oldValue, newValue);

            if (!fieldEquals) {
                diffItems.add(new DiffItem(tempField, oldValue, newValue));
            }
        }

        return diffItems;
    }


}
