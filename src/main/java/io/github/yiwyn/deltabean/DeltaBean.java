package io.github.yiwyn.deltabean;

import cn.hutool.core.util.StrUtil;
import io.github.yiwyn.deltabean.annotation.Diff;
import io.github.yiwyn.deltabean.annotation.IgnoreDiff;
import io.github.yiwyn.deltabean.common.enume.DiffMode;
import io.github.yiwyn.deltabean.entity.DiffContent;
import io.github.yiwyn.deltabean.entity.DiffItem;
import io.github.yiwyn.deltabean.impl.diffmode.BeanTypeDiffModeStrategy;
import io.github.yiwyn.deltabean.impl.diffmode.TemplateDiffModeStrategy;
import io.github.yiwyn.deltabean.interfaceable.BaseDiffModeStrategy;
import io.github.yiwyn.deltabean.interfaceable.BaseDiffTmpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: DeltaBean
 * @author: Yiwyn
 * @date: 2025/12/13 23:35
 * @Version: 1.0
 * @description:
 */
public class DeltaBean {


    public static <T> DiffExecutor<T> diffExecutor() {
        init();
        return new DiffExecutor<>();
    }

    // 处理模式策略
    private final static Map<DiffMode, BaseDiffModeStrategy> DIFF_MODE_STRATEGY_MAP = new HashMap<>();

    /**
     * 初始化方法
     */
    public static void init() {
        DIFF_MODE_STRATEGY_MAP.put(DiffMode.BEAN_TYPE_MODE, new BeanTypeDiffModeStrategy());
        DIFF_MODE_STRATEGY_MAP.put(DiffMode.TEMPLATE_MODE, new TemplateDiffModeStrategy());
    }


    public static class DiffExecutor<T> {

        private BaseDiffTmpl<? extends T> tmplBean;

        private DiffMode diffMode = DiffMode.BEAN_TYPE_MODE;

        public DiffExecutor<T> diffTemplate(BaseDiffTmpl<? extends T> tmplBean) {
            this.tmplBean = tmplBean;
            this.diffMode = DiffMode.TEMPLATE_MODE;
            return this;
        }

        // 结束命令
        public DiffContent diff(T oldObj, T newObj) throws Exception {


            // 根据模版模式，获取策略
            BaseDiffModeStrategy diffModeStrategy = DIFF_MODE_STRATEGY_MAP.get(this.diffMode);

            Context context = new Context(tmplBean, diffMode, oldObj, newObj);
            // 获取需要实体类/模版中需要对比的字段
            List<Field> fields = diffModeStrategy.diffFields(context);

            // 排除掉需要忽略的字段
            List<Field> templateFields = fields.stream().filter(p -> !p.isAnnotationPresent(IgnoreDiff.class)).toList();

            // 上下文设置需要处理的字段
            context.setTemplateFields(templateFields);
            List<DiffItem> diffItems = diffModeStrategy.diff(context);

            // 处理注解参数
            for (DiffItem diffItem : diffItems) {
                Field field = diffItem.getField();

                if (field.isAnnotationPresent(Diff.class)) {
                    Diff diffAnnotation = field.getAnnotation(Diff.class);
                    String diffFieldName = diffAnnotation.diffFieldName();
                    if (StrUtil.isNotBlank(diffFieldName)) {
                        diffItem.setFieldName(diffFieldName);
                    }
                    if (tmplBean == null) {
                        continue;
                    }
                    String eventId = diffAnnotation.onDiffEventId();
                    if (StrUtil.isBlank(eventId)) {
                        // 如果没有设置eventId， 默认使用fieldName 作为事件id
                        eventId = diffItem.getField().getName();
                    }
                    // 触发转译
                    tmplBean.triggerTrans(eventId, diffItem);

                } else {
                    diffItem.setNewValueTrans(diffItem.getNewValue());
                    diffItem.setOldValueTrans(diffItem.getOldValue());
                }
            }

            // 输出结果
            DiffContent content = new DiffContent(oldObj.getClass());

            content.setDiffItems(diffItems);

            return content;
        }

    }

    /**
     * 上下文对象
     */
    public static class Context {

        private final BaseDiffTmpl<?> diffTmplBean;

        private final DiffMode diffMode;

        private final Object oldValue;

        private final Object newValue;

        private List<DiffItem> diffItems = new ArrayList<>();

        private List<Field> templateFields = new ArrayList<>();

        public List<Field> getTemplateFields() {
            return templateFields;
        }

        public void setTemplateFields(List<Field> templateFields) {
            this.templateFields = templateFields;
        }

        public List<DiffItem> getDiffItems() {
            return diffItems;
        }

        public void setDiffItems(List<DiffItem> diffItems) {
            this.diffItems = diffItems;
        }

        public BaseDiffTmpl<?> getDiffTmplBean() {
            return diffTmplBean;
        }

        public DiffMode getDiffMode() {
            return diffMode;
        }

        public Object getOldObject() {
            return oldValue;
        }

        public Object getNewObject() {
            return newValue;
        }

        public Context(BaseDiffTmpl<?> diffTmplBean, DiffMode diffMode, Object oldValue, Object newValue) {
            this.diffTmplBean = diffTmplBean;
            this.diffMode = diffMode;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
    }


}
