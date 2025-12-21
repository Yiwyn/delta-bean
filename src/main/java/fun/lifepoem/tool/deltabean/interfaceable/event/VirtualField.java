package fun.lifepoem.tool.deltabean.interfaceable.event;


/**
 * 虚拟字段返回值，
 *
 * @param <T>
 */
@FunctionalInterface
public interface VirtualField<T> {

    /**
     * 虚拟字段发生变更时事件
     *
     * @param object 对比的对象
     */
    Object onVirtualFieldDiffEvent(T object);

}
