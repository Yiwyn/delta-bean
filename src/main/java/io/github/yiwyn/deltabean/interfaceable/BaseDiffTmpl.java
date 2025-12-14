package io.github.yiwyn.deltabean.interfaceable;

import io.github.yiwyn.deltabean.entity.DiffItem;
import io.github.yiwyn.deltabean.interfaceable.event.OnFieldDiffEvent;

import java.lang.reflect.Field;
import java.util.AbstractList;
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


    private final Map<String, OnFieldDiffEvent> fieldOnFieldDiffEventMap = new HashMap<>();

    public void triggerTrans(String eventId, DiffItem item) {
        OnFieldDiffEvent onFieldDiffEvent = fieldOnFieldDiffEventMap.get(eventId);
        if (onFieldDiffEvent == null) {
            return;
        }
        onFieldDiffEvent.onDiff(item);
    }

    public BaseDiffTmpl() {
        addFieldDiffEvent(fieldOnFieldDiffEventMap);
    }

    public abstract void addFieldDiffEvent(Map<String, OnFieldDiffEvent> fieldOnFieldDiffEventMap);

}
