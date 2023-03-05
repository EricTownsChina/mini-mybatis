package priv.eric.mini.mybatis.test.ths.miner;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Desc: 工具方法
 *
 * @author EricTownsChina@outlook.com
 * create 2023/3/4 23:27
 */
public class Toolbox {

    /**
     * 对象Object转Map
     *
     * @param obj 待转换对象
     * @return Map
     */
    public static Map<String, Object> toMap(Object obj) {
        try {
            if (Objects.isNull(obj)) return new HashMap<>(0);
            Map<String, Object> map;
            if (obj instanceof Map) {
                Map<?, ?> objMap = (Map<?, ?>) obj;
                map = new HashMap<>(objMap.size());
                for (Map.Entry<?, ?> entry : objMap.entrySet()) {
                    if (entry.getKey() == null) {
                        map.put(null, entry.getValue());
                    } else {
                        map.put(String.valueOf(entry.getKey()), entry.getValue());
                    }
                }
            } else {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                map = new HashMap<>(declaredFields.length);
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }
            }
            return map;
        } catch (IllegalAccessException exception) {
            throw new IllegalArgumentException("obj转换map失败");
        }
    }

    /**
     * 对象Object转List[Map]
     *
     * @param obj 待转换对象
     * @return List[Map]
     */
    public static List<Map<String, Object>> toList(Object obj) {
        if (Objects.isNull(obj)) return new ArrayList<>(0);
        Collection<?> collectionObj = (Collection<?>) obj;
        List<Map<String, Object>> listObj = new ArrayList<>(collectionObj.size());
        for (Object o : collectionObj) {
            Map<String, Object> mapObj = toMap(o);
            listObj.add(mapObj);
        }
        return listObj;
    }

    public static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException ex) {
                try {
                    Float.parseFloat(str);
                    return true;
                } catch (NumberFormatException exx) {
                    return false;
                }
            }
        }
    }
}
