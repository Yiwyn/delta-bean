package io.github.yiwyn.deltabean;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yiwyn.deltabean.entity.DiffContent;
import io.github.yiwyn.deltabean.entity.DiffItem;
import io.github.yiwyn.deltabean.tmpl.BaseDiffTmpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: DeltaBean
 * @author: Yiwyn
 * @date: 2025/12/13 23:35
 * @Version: 1.0
 * @description:
 */
public class DeltaBean {


    public static <T> DiffExecutor<T> diffExecutor() {
        return new DiffExecutor<>();
    }


    public static class DiffExecutor<T> {

        private Class<? extends BaseDiffTmpl<T>> diffTmplClazz;

        public DiffExecutor<T> diffTmpl(Class<? extends BaseDiffTmpl<T>> clazz) {
            this.diffTmplClazz = clazz;
            return this;
        }

        // 结束命令
        public DiffContent diff(T oldObj, T newObj) throws IllegalAccessException {

            Field[] tempFields;
            if (diffTmplClazz == null) {
                tempFields = ReflectUtil.getFields(oldObj.getClass());
            } else {
                tempFields = ReflectUtil.getFields(diffTmplClazz);
            }

            List<DiffItem> diffItems = new ArrayList<>();
            DiffContent content = new DiffContent();
            content.setDiffItems(diffItems);

            for (Field tempField : tempFields) {
                tempField.setAccessible(true);

                // 从模版中获取对应对象的值
                Object oldValue = tempField.get(oldObj);
                Object newValue = tempField.get(newObj);

                // 获取模版参数类型
                Class<?> tempFieldType = tempField.getType();

                boolean fieldEquals = true;
                if (tempFieldType.isAssignableFrom(String.class)) {
                    fieldEquals = StrUtil.equals((CharSequence) newValue, (CharSequence) oldValue);
                } else if (tempFieldType.isAssignableFrom(BigDecimal.class)) {
                    fieldEquals = NumberUtil.equals((BigDecimal) oldValue, (BigDecimal) newValue);
                }

                if (!fieldEquals) {
                    diffItems.add(new DiffItem(oldValue, newValue));
                }


            }


            return content;
        }

    }


}
