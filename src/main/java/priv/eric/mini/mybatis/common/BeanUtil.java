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
     * @param <T>   强转类型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

}

