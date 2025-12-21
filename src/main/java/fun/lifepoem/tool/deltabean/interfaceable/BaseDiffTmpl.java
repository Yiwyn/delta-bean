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
 * @description: 比较模版
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
     * @param eventId   事件id
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
     * 基类构造方法，构造时可将自定义含义事件加入其中
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
