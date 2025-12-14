package io.github.yiwyn.deltabean.interfaceable.event;

import io.github.yiwyn.deltabean.entity.DiffItem;

@FunctionalInterface
public interface OnFieldDiffEvent {

    void onDiff(DiffItem item);

}
