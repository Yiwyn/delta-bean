package fun.lifepoem.tool.deltabean;

import cn.hutool.core.util.StrUtil;
import fun.lifepoem.tool.deltabean.annotation.Diff;
import fun.lifepoem.tool.deltabean.annotation.IgnoreDiff;
import fun.lifepoem.tool.deltabean.common.enume.DiffMode;
import fun.lifepoem.tool.deltabean.entity.DiffContent;
import fun.lifepoem.tool.deltabean.entity.DiffItem;
import fun.lifepoem.tool.deltabean.impl.diffmode.BeanTypeDiffModeStrategy;
import fun.lifepoem.tool.deltabean.impl.diffmode.TemplateDiffModeStrategy;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffModeStrategy;
import fun.lifepoem.tool.deltabean.interfaceable.BaseDiffTmpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: DeltaBean
 * @author: Yiwyn
 * @date: 2025/12/13 23:35
 * @Version: 1.0
 * Java Bean 差异比较入口，默认按 Bean 字段比较，也可使用模板指定字段。
 */
public class DeltaBean {


    /** 创建 Bean 类型模式的比较执行器。 */
    public static <T> DiffExecutor<T> diffExecutor(Class<T> clazz) {
        init();
        return new DiffExecutor<>(clazz);
    }

    // 处理模式策略
    private final static Map<DiffMode, BaseDiffModeStrategy> DIFF_MODE_STRATEGY_MAP = new HashMap<>();

    /**
     * 初始化方法
     */
    private static void init() {
        DIFF_MODE_STRATEGY_MAP.put(DiffMode.BEAN_TYPE_MODE, new BeanTypeDiffModeStrategy());
        DIFF_MODE_STRATEGY_MAP.put(DiffMode.TEMPLATE_MODE, new TemplateDiffModeStrategy());
    }


    public static class DiffExecutor<T> {

        private BaseDiffTmpl<T> tmplBean;

        private DiffMode diffMode = DiffMode.BEAN_TYPE_MODE;


        private final Class<T> diffClazz;

        /** 切换到模板模式；模板字段按名称匹配实体字段。 */
        public DiffExecutor<T> diffTemplate(BaseDiffTmpl<T> tmplBean) {
            this.tmplBean = tmplBean;
            this.diffMode = DiffMode.TEMPLATE_MODE;
            return this;
        }

        /**
         * 比较方法，执行方法后可以得到差异内容。
         *
         * @param oldObj 比较对象-老
         * @param newObj 比较对象-新
         * @return 包含比较类和差异项目的结果
         * @throws Exception 反射访问或策略执行失败时传播原始异常
         */
        public DiffContent diff(T oldObj, T newObj) throws Exception {

            // 根据模版模式，获取策略
            BaseDiffModeStrategy diffModeStrategy = DIFF_MODE_STRATEGY_MAP.get(this.diffMode);

            Context context = new Context(tmplBean, diffClazz, diffMode, oldObj, newObj);
            // 获取需要实体类/模版中需要对比的字段
            List<Field> fields = diffModeStrategy.diffFields(context);

            // 排除掉需要忽略的字段
            List<Field> templateFields = fields.stream().filter(p -> !p.isAnnotationPresent(IgnoreDiff.class)).collect(Collectors.toList());

            // 上下文设置需要处理的字段
            context.setTemplateFields(templateFields);
            List<DiffItem> diffItems = diffModeStrategy.diff(context);

            // 处理注解参数
            for (DiffItem diffItem : diffItems) {
                Field field = diffItem.getField();

                String fieldId = field.getName();

                if (field.isAnnotationPresent(Diff.class)) {
                    Diff diffAnnotation = field.getAnnotation(Diff.class);
                    String diffFieldName = diffAnnotation.diffFieldId();
                    if (StrUtil.isNotBlank(diffFieldName)) {
                        diffItem.setFieldId(diffFieldName);
                    }

                    // 字段含义
                    String fieldDesc = diffAnnotation.diffFieldDesc();
                    if (StrUtil.isNotBlank(fieldDesc)) {
                        diffItem.setFieldDesc(fieldDesc);
                    }

                    if (tmplBean == null) {
                        continue;
                    }
                    String eventId = diffAnnotation.onDiffEventId();
                    if (StrUtil.isBlank(eventId)) {
                        // 如果没有设置eventId， 默认使用fieldName 作为事件id
                        eventId = fieldId;
                    }
                    // 触发转译
                    tmplBean.triggerTrans(eventId, diffItem, oldObj, newObj);
                }
            }

            // 输出结果
            return new DiffContent(oldObj.getClass(), diffItems);
        }

        /**
         * 构造执行器
         *
         * @param diffClazz 被比较的类的class信息
         */
        public DiffExecutor(Class<T> diffClazz) {
            this.diffClazz = diffClazz;
        }
    }

    /**
     * 策略执行上下文，保存比较对象、模式、待比较字段及差异列表。
     */
    public static class Context {
        /**
         * 模版bean信息
         */
        private final BaseDiffTmpl<?> diffTmplBean;

        /**
         * 比较模式
         */
        private final DiffMode diffMode;

        /**
         * 比较对象-老
         */
        private final Object oldValue;

        /**
         * 比较对象-新
         */
        private final Object newValue;

        /**
         * 变更项目
         */
        private List<DiffItem> diffItems = new ArrayList<>();

        /**
         * 比较的模版字段，不同的比较模式得出的字段不一致
         */
        private List<Field> templateFields = new ArrayList<>();

        /**
         * 变更的类的class信息
         */
        private final Class<?> diffClazz;

        /** @return 当前待比较字段 */
        public List<Field> getTemplateFields() {
            return templateFields;
        }

        public void setTemplateFields(List<Field> templateFields) {
            this.templateFields = templateFields;
        }

        /** @return 当前差异项目 */
        public List<DiffItem> getDiffItems() {
            return diffItems;
        }

        public void setDiffItems(List<DiffItem> diffItems) {
            this.diffItems = diffItems;
        }

        /** @return 模板实例，Bean 类型模式下为 {@code null} */
        public BaseDiffTmpl<?> getDiffTmplBean() {
            return diffTmplBean;
        }

        /** @return 比较模式 */
        public DiffMode getDiffMode() {
            return diffMode;
        }

        /** @return 旧对象 */
        public Object getOldObject() {
            return oldValue;
        }

        /** @return 新对象 */
        public Object getNewObject() {
            return newValue;
        }

        /** @return 比较类 */
        public Class<?> getDiffClazz() {
            return diffClazz;
        }

        public Context(BaseDiffTmpl<?> diffTmplBean, Class<?> diffClazz, DiffMode diffMode, Object oldValue, Object newValue) {
            this.diffTmplBean = diffTmplBean;
            this.diffMode = diffMode;
            this.oldValue = oldValue;
            this.newValue = newValue;
            this.diffClazz = diffClazz;
        }
    }


}
