package priv.eric.mini.mybatis.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static priv.eric.mini.mybatis.common.Constant.DOT;
import static priv.eric.mini.mybatis.common.Constant.SLASH;

/**
 * Description: 类型查找发现工具类
 *
 * @author EricTowns
 * @date 2023/2/6 13:49
 */
public class ScanUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanUtil.class);

    private static final String FILE = "file";

    private ScanUtil() {
    }

    /**
     * 扫描包下的所有class
     *
     * @param packageName 包名称, 例: com.xx.xxx.xx
     * @return class列表
     */
    public static List<Class<?>> scanClasses(String packageName) {
        if (!StringUtils.hasText(packageName)) {
            return new ArrayList<>(0);
        }
        packageName = packageName.replace(DOT, SLASH);
        URL url = Thread.currentThread().getContextClassLoader().getResource(packageName);
        if (url == null) {
            return new ArrayList<>(0);
        }
        if (FILE.equals(url.getProtocol())) {
            File file = new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8));
            return scanFile(file);
        } else {
            throw new IllegalArgumentException("暂不支持扫描jar包");
        }
    }

    private static List<Class<?>> scanFile(File file) {
        List<Class<?>> classList = new ArrayList<>();
        if (file.isFile()) {
            String path = file.getAbsolutePath();
            if (path.endsWith(".class")) {
                String className = path.substring(0, path.length() - 6).replace(SLASH, DOT);
                Class<?> clazz = loadClass(className);
                classList.add(clazz);
            }
            return classList;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return classList;
            } else {
                for (File subFile : files) {
                    classList.addAll(scanFile(subFile));
                }
            }
            return classList;
        } else {
            return classList;
        }
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类{}失败, 错误: ", className, e);
            throw new RuntimeException(e);
        }
    }

}
