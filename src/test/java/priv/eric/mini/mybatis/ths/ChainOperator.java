package priv.eric.mini.mybatis.ths;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 顺子的代码: 链式获取对象中的属性
 *
 * @author EricTowns
 * @date 2023/2/8 23:09
 */
public class ChainOperator {

    public static final String ARRAY = "ARRAY";
    public static final String OBJECT = "OBJECT";
    public static final String NULL = "NULL";

//    private final Map<String, Object> repo;
    private String preType;
    private Object preValue;
    private final String express;
    private final String[] segments;

    public ChainOperator(String express) {
        if (!express.startsWith("$")) {
            throw new IllegalArgumentException("表达式需要以$开头");
        }
        if (!express.contains(".")) {
            throw new IllegalArgumentException("请用.指定需要获取的属性");
        }
        this.express = express;
        String[] originSegments = express.split("\\.");
        this.preValue = getRepo(originSegments[0]);
        this.preType = OBJECT;
        this.segments = Arrays.copyOfRange(originSegments, 1, originSegments.length);
    }

    public static Map<String, Object> getRepo(String express) {
        Map<String, Object> repo = new HashMap<>();
        List<String> arr = Stream.of("1", "2", "3").collect(Collectors.toList());
        repo.put("arr", arr);
        repo.put("obj", new Object());
        return repo;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        try {
            if (obj == null) {
                return null;
            }

            Map<String, Object> map = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException exception) {
            throw new IllegalArgumentException("obj转换map失败");
        }
    }

    public Object get() {
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            if (i == 0) {
                this.preValue = objectToMap(preValue).get(segment);
                this.preType = valueType(segment, preValue);
            }

        }
        return this.preValue;
    }

    public void get(String segment) {
        if (NULL.equals(this.preType) && segment != null && segment.length() > 0) {
            throw new NullPointerException("获取" + segment + "属性失败, 上游属性值为null");
        }

        String express = segment.replace("[]", "");
        int indexFirstLeftBracket = express.indexOf("[");
        if (indexFirstLeftBracket != -1) {
            // 1.属性是数组, 2.需要从属性中取值
            String fieldExpress = express.substring(0, indexFirstLeftBracket);
            String arrayExpress = express.substring(indexFirstLeftBracket);
            // 先获取值
            Object thisValue = null;
            if (OBJECT.equals(this.preType)) {
                Map<String, Object> preMap = objectToMap(this.preValue);
                if (preMap == null) {
                    this.preValue = null;
                    this.preType = NULL;
                    return;
                } else {
                    thisValue = preMap.get(segment);
                }
                // 取数组中的值
                if (thisValue == null) {
                    this.preValue = null;
                    this.preType = NULL;
                    return;
                } else {
                    Object[] thisArr = ((Collection<?>) thisValue).toArray();
//                    String substring = arrayExpress.substring(0, "]");
                    // todo 字符串截取测试
                }

            }



        } else {
            // 没有[, 不需要从数组属性中取值, 只取值就行
            if (OBJECT.equals(this.preType)) {
                Map<String, Object> preMap = objectToMap(this.preValue);
                if (preMap == null) {
                    this.preValue = null;
                    this.preType = NULL;
                } else {
                    this.preValue = preMap.get(segment);
                    this.preType = valueType(segment, this.preValue);
                }
            } else if (ARRAY.equals(this.preType)){
                Object[] preArr = ((Collection<?>) preValue).toArray();
                this.preValue = Arrays.stream(preArr)
                        .map(ChainOperator::objectToMap)
                        .map(m -> {
                            if (m == null) {
                                return null;
                            } else {
                                return m.get(express);
                            }
                        })
                        .collect(Collectors.toList());
            } else {
                this.preValue = null;
                this.preType = NULL;
            }
        }
    }

    private String valueType(String segment, Object value) {
        if (value == null) {
            return NULL;
        }
        boolean assignArrType = segment.endsWith("[]");
        boolean trueArrType = value instanceof Collection;
        if (assignArrType && !trueArrType) {
            throw new IllegalArgumentException("表达式中" + segment + "属性类型不为数组!");
        } else if (trueArrType) {
            return ARRAY;
        } else {
            return OBJECT;
        }
    }


}
