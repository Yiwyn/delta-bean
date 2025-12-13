package io.github.yiwyn.deltabean.evnet;

import io.github.yiwyn.deltabean.entity.DiffItem;

@FunctionalInterface
public interface OnFieldDiffEvent {

    DiffItem onDiff(DiffItem item);

}
