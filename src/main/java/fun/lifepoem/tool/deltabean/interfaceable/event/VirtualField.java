package fun.lifepoem.tool.deltabean.interfaceable.event;


@FunctionalInterface
public interface VirtualField<T> {

    Object onVirtualFieldDiffEvent(T object);

}
