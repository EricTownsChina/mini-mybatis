package priv.eric.mini.mybatis.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 类型查找发现工具类
 *
 * @author EricTowns
 * @date 2023/2/6 13:49
 */
public class ScanUtil {

    private static final String FILE = "file";

    private ScanUtil() {
    }

    public static List<Class<?>> scanClasses(String packageName) {
        // TODO: 2023/2/6 补全代码
        URL url = Thread.currentThread().getContextClassLoader().getResource(packageName);
        String protocol = url.getProtocol();
        if (FILE.equals(protocol)) {

        }


        return new ArrayList<>(0);
    }

}
