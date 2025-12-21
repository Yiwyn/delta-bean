package fun.lifepoem.tool.deltabean.interfaceable.event;

/**
 * 字段变更后转译事件
 * e.g.
 * userId:1 ==> userName:张三
 *
 * @param <T> 比较变更对象类型
 */
@FunctionalInterface
public interface OnFieldDiffTransEvent<T> {

    Object onDiff(T data);

}
