package fun.lifepoem.tool.deltabean.impl.diffmode;

import cn.hutool.core.util.ReflectUtil;
import fun.lifepoem.tool.deltabean.DeltaBean;
import fun.lifepoem.tool.deltabean.annotation.VirtualDiff;
import fun.lifepoem.tool.deltabean.entity.DiffItem;
import fun.lifepoem.tool.deltabean.exception.DetailBeanTypeMismatchException;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffModeStrategy;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffTmpl;
import fun.lifepoem.tool.deltabean.interfaceable.event.VirtualField;
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


        //  获取模版bean，用于处理模版字段
        BaseDiffTmpl<?> diffTmplBean = context.getDiffTmplBean();

        for (Field tempField : diffFields) {

            String fieldName = tempField.getName();

            Object oldValue;
            Object newValue;

            if (tempField.isAnnotationPresent(VirtualDiff.class)) {
                Class<?> type = tempField.getType();
                // 如果返回值类型是 DiffTmplField 则认为需要执行字段事件
                if (type.isAssignableFrom(VirtualField.class)) {
                    tempField.setAccessible(true);
                    VirtualField tmplFieldEvent = (VirtualField) tempField.get(diffTmplBean);
                    oldValue = tmplFieldEvent.onVirtualFieldDiffEvent(oldObj);
                    newValue = tmplFieldEvent.onVirtualFieldDiffEvent(newObj);
                } else {
                    throw new DetailBeanTypeMismatchException(fieldName + "虚拟字段标识返回值类型错误!");
                }
            } else {
                // 从模版中获取对应对象的值
                Field field = ReflectUtil.getField(oldObj.getClass(), fieldName);
                if (field == null) {
                    continue;
                }

                field.setAccessible(true);
                oldValue = field.get(oldObj);
                newValue = field.get(newObj);
            }


            // 获取模版参数类型
            boolean fieldEquals = CompleteEqualsUtil.equals(oldValue, newValue);

            if (!fieldEquals) {
                diffItems.add(new DiffItem(tempField, oldValue, newValue));
            }
        }

        return diffItems;
    }


}
