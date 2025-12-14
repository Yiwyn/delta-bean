package io.github.yiwyn.deltabean.impl.diffmode;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yiwyn.deltabean.DeltaBean;
import io.github.yiwyn.deltabean.entity.DiffItem;
import io.github.yiwyn.deltabean.interfaceable.BaseDiffModeStrategy;
import io.github.yiwyn.deltabean.interfaceable.BaseDiffTmpl;
import io.github.yiwyn.deltabean.utils.CompleteEqualsUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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
        return List.of(fields);
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
