package fun.lifepoem.tool.deltabean.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * 终极版通用对象相等判断工具类（修复枚举对比的工具提示问题）
 * 支持：基本类型、包装类、浮点数、字符串、枚举、数组、集合、Map、BigDecimal、日期时间、Optional、UUID、URI/URL、File/Path、CharSequence等
 */
public class CompleteEqualsUtil {

    // 浮点数精度阈值（可根据业务调整）
    private static final double DOUBLE_THRESHOLD = 1e-9;
    private static final float FLOAT_THRESHOLD = 1e-6f;
    // BigDecimal 比较的小数位数（默认精确到小数点后9位，可调整）
    private static final int BIG_DECIMAL_SCALE = 9;
    private static final RoundingMode BIG_DECIMAL_ROUND_MODE = RoundingMode.HALF_UP;

    /**
     * 通用相等判断入口方法
     *
     * @param a 第一个对象
     * @param b 第二个对象
     * @return 两个对象是否值相等（全类型适配）
     */
    public static boolean equals(Object a, Object b) {
        // 1. 引用相等 或 均为null → true
        if (a == b) {
            return true;
        }
        // 2. 其中一个为null → false
        if (a == null || b == null) {
            return false;
        }
        // 3. 类型完全不一致 → false（子类和父类视为不同类型，如String和Object）
        Class<?> clazzA = a.getClass();
        Class<?> clazzB = b.getClass();
        if (!clazzA.equals(clazzB)) {
            // 特殊处理：所有CharSequence子类（String/StringBuilder/StringBuffer）视为同类型对比
            if (CharSequence.class.isAssignableFrom(clazzA) && CharSequence.class.isAssignableFrom(clazzB)) {
                return equalsCharSequence((CharSequence) a, (CharSequence) b);
            }
            return false;
        }

        // 4. 按具体类型精细化处理
        // 4.1 基本类型包装类
        if (clazzA == Byte.class) return ((Byte) a).equals(b);
        if (clazzA == Short.class) return ((Short) a).equals(b);
        if (clazzA == Integer.class) return ((Integer) a).equals(b);
        if (clazzA == Long.class) return ((Long) a).equals(b);
        if (clazzA == Character.class) return ((Character) a).equals(b);
        if (clazzA == Boolean.class) return ((Boolean) a).equals(b);

        // 4.2 浮点数（处理精度问题）
        if (clazzA == Float.class) {
            float valA = (Float) a;
            float valB = (Float) b;
            return Math.abs(valA - valB) < FLOAT_THRESHOLD;
        }
        if (clazzA == Double.class) {
            double valA = (Double) a;
            double valB = (Double) b;
            return Math.abs(valA - valB) < DOUBLE_THRESHOLD;
        }

        // 4.3 字符串及CharSequence子类（String/StringBuilder/StringBuffer）
        if (clazzA == String.class) return ((String) a).equals(b);
        if (CharSequence.class.isAssignableFrom(clazzA)) {
            return equalsCharSequence((CharSequence) a, (CharSequence) b);
        }

        // 4.4 枚举（修复工具误判提示，保留单例对比逻辑）
        if (clazzA.isEnum()) {
            // 枚举的equals底层就是this == obj，既消除提示又保证正确性
            return a.equals(b);
            // 若追求极致性能，也可保留==，并通过注释抑制提示：
            // noinspection ConstantConditions
            // return a == b;
        }

        // 4.5 数组（一维/多维，含基本类型和引用类型）
        if (clazzA.isArray()) return equalsArray(a, b);

        // 4.6 集合（List/Set/Queue）
        if (a instanceof Collection) return equalsCollection((Collection<?>) a, (Collection<?>) b);

        // 4.7 Map（HashMap/TreeMap等）
        if (a instanceof Map) return equalsMap((Map<?, ?>) a, (Map<?, ?>) b);

        // 4.8 BigDecimal（高精度数值，处理scale和round）
        if (clazzA == BigDecimal.class) {
            return equalsBigDecimal((BigDecimal) a, (BigDecimal) b);
        }

        // 4.9 日期时间类（JDK8+新时间API + 传统Date）
        if (a instanceof Date) return equalsDate((Date) a, (Date) b);
        if (a instanceof LocalDate) return ((LocalDate) a).equals(b);
        if (a instanceof LocalTime) return ((LocalTime) a).equals(b);
        if (a instanceof LocalDateTime) return ((LocalDateTime) a).equals(b);
        if (a instanceof ZonedDateTime) return ((ZonedDateTime) a).equals(b);
        if (a instanceof Instant) return ((Instant) a).equals(b);
        if (a instanceof OffsetDateTime) return ((OffsetDateTime) a).equals(b);
        if (a instanceof OffsetTime) return ((OffsetTime) a).equals(b);
        if (a instanceof Year) return ((Year) a).equals(b);
        if (a instanceof YearMonth) return ((YearMonth) a).equals(b);
        if (a instanceof MonthDay) return ((MonthDay) a).equals(b);
        if (a instanceof ChronoLocalDate) return ((ChronoLocalDate) a).equals(b);
        if (a instanceof ChronoLocalDateTime) return ((ChronoLocalDateTime<?>) a).equals(b);
        if (a instanceof Temporal) return equalsTemporal((Temporal) a, (Temporal) b);

        // 4.10 Optional/OptionalInt/OptionalLong/OptionalDouble
        if (a instanceof Optional) return equalsOptional((Optional<?>) a, (Optional<?>) b);
        if (a instanceof OptionalInt) return ((OptionalInt) a).equals(b);
        if (a instanceof OptionalLong) return ((OptionalLong) a).equals(b);
        if (a instanceof OptionalDouble) return ((OptionalDouble) a).equals(b);

        // 4.11 UUID
        if (clazzA == UUID.class) return ((UUID) a).equals(b);

        // 4.12 URI/URL
        if (clazzA == URI.class) return ((URI) a).equals(b);
        if (clazzA == URL.class) return ((URL) a).equals(b);

        // 4.13 File/Path（文件路径对比）
        if (clazzA == File.class) return equalsFile((File) a, (File) b);
        if (a instanceof Path) return ((Path) a).equals(b);

        // 4.14 自定义对象（依赖重写的equals方法）
        return a.equals(b);
    }

    // ------------------- 细分类型对比方法 -------------------

    /**
     * CharSequence子类对比（String/StringBuilder/StringBuffer）
     */
    private static boolean equalsCharSequence(CharSequence a, CharSequence b) {
        if (a.length() != b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }

    /**
     * 数组对比（一维/多维，处理浮点数精度）
     */
    private static boolean equalsArray(Object a, Object b) {
        // 基本类型数组
        if (a instanceof int[]) return Arrays.equals((int[]) a, (int[]) b);
        if (a instanceof long[]) return Arrays.equals((long[]) a, (long[]) b);
        if (a instanceof short[]) return Arrays.equals((short[]) a, (short[]) b);
        if (a instanceof byte[]) return Arrays.equals((byte[]) a, (byte[]) b);
        if (a instanceof char[]) return Arrays.equals((char[]) a, (char[]) b);
        if (a instanceof boolean[]) return Arrays.equals((boolean[]) a, (boolean[]) b);
        // 浮点数数组（处理精度）
        if (a instanceof float[]) return equalsFloatArray((float[]) a, (float[]) b);
        if (a instanceof double[]) return equalsDoubleArray((double[]) a, (double[]) b);
        // 引用类型数组（多维）
        return Arrays.deepEquals((Object[]) a, (Object[]) b);
    }

    /**
     * float数组对比（精度处理）
     */
    private static boolean equalsFloatArray(float[] a, float[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) >= FLOAT_THRESHOLD) return false;
        }
        return true;
    }

    /**
     * double数组对比（精度处理）
     */
    private static boolean equalsDoubleArray(double[] a, double[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) >= DOUBLE_THRESHOLD) return false;
        }
        return true;
    }

    /**
     * 集合对比（List/Set/Queue，区分有序/无序）
     */
    private static boolean equalsCollection(Collection<?> a, Collection<?> b) {
        if (a.size() != b.size()) return false;

        // Set/Queue（无序）：检查元素存在性+数量一致性
        if (a instanceof Set || a instanceof Queue) {
            for (Object elem : a) {
                if (!b.contains(elem) || Collections.frequency(a, elem) != Collections.frequency(b, elem)) {
                    return false;
                }
            }
            return true;
        }

        // List（有序）：按索引逐一对比
        Iterator<?> itA = a.iterator();
        Iterator<?> itB = b.iterator();
        while (itA.hasNext() && itB.hasNext()) {
            Object elemA = itA.next();
            Object elemB = itB.next();
            if (!equals(elemA, elemB)) return false;
        }
        return true;
    }

    /**
     * Map对比（键值对全量校验）
     */
    private static boolean equalsMap(Map<?, ?> a, Map<?, ?> b) {
        if (a.size() != b.size()) return false;

        for (Entry<?, ?> entry : a.entrySet()) {
            Object key = entry.getKey();
            Object valA = entry.getValue();
            Object valB = b.get(key);

            if (!b.containsKey(key) || !equals(valA, valB)) return false;
        }
        return true;
    }

    /**
     * BigDecimal对比（处理scale和精度，避免0.1和0.10不等的问题）
     */
    private static boolean equalsBigDecimal(BigDecimal a, BigDecimal b) {
        // 统一scale后对比
        return a.setScale(BIG_DECIMAL_SCALE, BIG_DECIMAL_ROUND_MODE)
                .equals(b.setScale(BIG_DECIMAL_SCALE, BIG_DECIMAL_ROUND_MODE));
        // 若需严格对比（包括scale），直接用：return a.equals(b);
    }

    /**
     * 传统Date对比（按时间戳）
     */
    private static boolean equalsDate(Date a, Date b) {
        return a.getTime() == b.getTime();
    }

    /**
     * Temporal接口实现类对比（通用时间类型）
     */
    private static boolean equalsTemporal(Temporal a, Temporal b) {
        return a.until(b, ChronoUnit.NANOS) == 0;
    }

    /**
     * Optional对比（空值+内部值）
     */
    private static boolean equalsOptional(Optional<?> a, Optional<?> b) {
        if (a.isPresent() != b.isPresent()) return false;
        if (!a.isPresent()) return true; // 均为空
        return equals(a.get(), b.get());
    }

    /**
     * File对比（路径+文件状态，可按需简化为仅路径对比）
     */
    private static boolean equalsFile(File a, File b) {
        // 基础：路径对比（跨平台兼容）
        return a.getAbsolutePath().equals(b.getAbsolutePath());
        // 进阶：文件存在性+最后修改时间+大小（按需开启）
        // if (a.exists() != b.exists()) return false;
        // if (a.exists() && (a.lastModified() != b.lastModified() || a.length() != b.length())) return false;
    }

}