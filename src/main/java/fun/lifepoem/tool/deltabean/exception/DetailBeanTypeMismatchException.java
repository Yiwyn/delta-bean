package fun.lifepoem.tool.deltabean.exception;

/** 虚拟字段声明类型不是 {@code VirtualField} 时抛出的运行时异常。 */
public class DetailBeanTypeMismatchException extends RuntimeException {

    /** @param message 描述类型不匹配原因 */
    public DetailBeanTypeMismatchException(String message) {
        super(message);
    }
}
