package fun.lifepoem.tool.deltabean.interfaceable.event;


/**
 * 模板中的虚拟字段回调，返回参与比较的值。
 *
 * @param <T>
 */
@FunctionalInterface
public interface VirtualField<T> {

    /**
     * 虚拟字段发生变更时事件
     *
     * @param object 对比的旧对象或新对象
     * @return 用于比较的虚拟值
     */
    Object onVirtualFieldDiffEvent(T object);

}
