package fun.lifepoem.tool.deltabean.interfaceable;

import fun.lifepoem.tool.deltabean.entity.DiffItem;
import fun.lifepoem.tool.deltabean.interfaceable.event.OnFieldDiffTransEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: BaseCompareTmpl
 * @author: Yiwyn
 * @date: 2025/12/13 23:41
 * @Version: 1.0
 * 模板模式的字段和转换事件注册基类。
 * base compare template
 */
public abstract class BaseDiffTmpl<T> {

    /**
     * 字段变更事件存储map
     * key: diffEventId [默认fieldName]
     * value：OnFieldDiffTransEvent 对应处理事件
     */
    private final Map<String, OnFieldDiffTransEvent<T>> onFieldDiffTransEventMap = new HashMap<>();

    /**
     * 转译触发
     *
     * @param eventId   事件 ID；由 {@code @Diff} 指定或回退为字段名
     * @param diffItem  变更项目
     * @param oldObject 比较对象-老
     * @param newObject 比较对象-新
     */
    public void triggerTrans(String eventId, DiffItem diffItem, T oldObject, T newObject) {
        OnFieldDiffTransEvent<T> onFieldDiffTransEvent = onFieldDiffTransEventMap.get(eventId);
        if (onFieldDiffTransEvent == null) {
            return;
        }
        diffItem.setNewValueTrans(onFieldDiffTransEvent.onDiff(newObject));
        diffItem.setOldValueTrans(onFieldDiffTransEvent.onDiff(oldObject));
    }

    /**
     * 构造模板并调用 {@link #addFieldDiffEvent(Map)} 注册事件。
     */
    public BaseDiffTmpl() {
        addFieldDiffEvent(onFieldDiffTransEventMap);
    }

    /**
     * 抽象函数，提示实现模版类用于添加自定义转移事件
     *
     * @param onFieldDiffTransEventMap 事件存储map，可将自定义方法put进入其中。
     */
    public abstract void addFieldDiffEvent(Map<String, OnFieldDiffTransEvent<T>> onFieldDiffTransEventMap);

}
