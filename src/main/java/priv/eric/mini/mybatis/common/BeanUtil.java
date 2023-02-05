package priv.eric.mini.mybatis.common;

/**
 * Description: Bean工具类
 *
 * @author EricTowns
 * @date 2023/2/5 15:41
 */
public class BeanUtil {

    private BeanUtil() {
    }

    /**
     * 强转对象类型, 主要是消除黄色的告警
     *
     * @param obj   原始对象
     * @param clazz 强转类型
     * @param <T>   泛型T
     * @return 转换后的对象
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

}

