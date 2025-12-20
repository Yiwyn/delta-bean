package fun.lifepoem.tool.deltabean.interfaceable.event;

import fun.lifepoem.tool.deltabean.entity.DiffItem;

@FunctionalInterface
public interface OnFieldDiffEvent {

    void onDiff(DiffItem item);

}
