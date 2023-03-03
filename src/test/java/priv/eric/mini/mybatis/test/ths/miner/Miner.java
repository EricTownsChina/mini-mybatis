package priv.eric.mini.mybatis.test.ths.miner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: 对象内属性获取器
 *
 * @author EricTowns
 * @date 2023/2/12 00:26
 */
public class Miner {
    /**
     * 原始指令
     */
    private final String order;
    /**
     * 获取列表值属性时候是否保留null值
     */
    private final boolean arraySaveNull;
    /**
     * 当前阶段的获取信息
     */
    private Platform platform;
    /**
     * 错误信息
     */
    private String accidentDesc;

    private Miner(Object mine, String order) {
        if (null == order || order.isEmpty()) {
            throw new IllegalArgumentException("构建获取器失败, 缺少指令表达式!");
        }
        this.platform = new Platform(null, order);
        this.platform.setOre(Ore.newInstance(mine));
        this.order = order;
        this.arraySaveNull = true;
    }

    public static Miner explore(Object mine, String order) {
        return new Miner(mine, order);
    }

    private static Map<String, Object> toMap(Object obj) {
        try {
            if (obj == null) return null;
            Map<String, Object> map = new HashMap<>();
            if (obj instanceof Map) {
                Map<?, ?> objMap = (Map<?, ?>) obj;
                for (Map.Entry<?, ?> entry : objMap.entrySet()) {
                    if (entry.getKey() == null) {
                        map.put(null, entry.getValue());
                    } else {
                        map.put(String.valueOf(entry.getKey()), entry.getValue());
                    }
                }
            } else {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
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

    private static boolean verify(String order) {
        // TODO: 2023/2/12 进行指令的校验
        return true;
    }

    public Object reward() {
        String standardOrder = this.order.substring(1);
        String[] orderSegments = standardOrder.split("\\.");
        for (String orderSegment : orderSegments) {
            Platform workPlatform = new Platform(this.platform, orderSegment);
            workPlatform.work();
            this.platform = workPlatform;
            if (!workPlatform.isExplore()) {
                break;
            }
        }
        this.platform.setExplore(false);
        return this.platform.getOre().getOreValue();
    }

    /**
     * 属性获取全记录
     *
     * @return Deque
     */
    public Deque<Platform> explorationDaily() {
        Deque<Platform> daily = new ArrayDeque<>(4);
        Platform endPlatform = this.platform;
        do {
            daily.addFirst(endPlatform);
            endPlatform = endPlatform.getPre();
        } while (endPlatform != null);
        return daily;
    }

    private static class Ore {
        private static final String OBJECT = "OBJECT";
        private static final String ARRAY = "ARRAY";
        private static final String NULL = "NULL";
        /**
         * 对象值
         */
        private Object oreValue;
        /**
         * 对象类型
         */
        private String oreType;

        private Ore(Object oreValue) {
            this.oreValue = oreValue;
            if (this.oreValue == null) {
                this.oreType = NULL;
            } else if (this.oreValue instanceof Collection) {
                this.oreType = ARRAY;
            } else {
                this.oreType = OBJECT;
            }
        }

        public static Ore newInstance(Object oreValue) {
            return new Ore(oreValue);
        }

        public Object getOreValue() {
            return oreValue;
        }

        public void setOreValue(Object oreValue) {
            this.oreValue = oreValue;
        }

        public String getOreType() {
            return oreType;
        }

        public void setOreType(String oreType) {
            this.oreType = oreType;
        }

        public boolean isNull() {
            return NULL.equals(this.oreType);
        }

        public boolean isArray() {
            return ARRAY.equals(this.oreType);
        }

        @Override
        public String toString() {
            return "Ore{" +
                    "oreValue=" + oreValue +
                    ", oreType='" + oreType + '\'' +
                    '}';
        }
    }

    private class Platform {
        /**
         * 上一层的情况
         */
        private final Platform pre;
        /**
         * 当前层指令
         */
        private final String order;
        /**
         * 当前层的对象信息
         */
        private Ore ore;
        /**
         * 是否继续获取
         */
        private boolean explore;

        public Platform(Platform pre, String order) {
            this.pre = pre;
            this.order = order;
            this.ore = null;
            this.explore = true;
        }

        public Platform getPre() {
            return pre;
        }

        public Ore getOre() {
            return ore;
        }

        public void setOre(Ore ore) {
            this.ore = ore;
        }

        public String getOrder() {
            return order;
        }

        public boolean isExplore() {
            return explore;
        }

        public void setExplore(Boolean explore) {
            this.explore = explore;
        }

        public void work() {
            if (pre.isExplore() && verify(this.order)) {
                Ore preOre = pre.getOre();
                int orderIndex = this.order.indexOf("[");
                if (orderIndex == -1) {
                    this.ore = extract(preOre, order);
                } else {
                    String valueOrder = this.order.substring(0, orderIndex);
                    String indexOrder = this.order.substring(orderIndex);
                    String[] indices = indexOrder.substring(1, indexOrder.length() - 1).split("]\\[");
                    Ore ore = extract(preOre, valueOrder);
                    if (ore.isNull()) {
                        this.ore = ore;
                        this.explore = false;
                        return;
                    }
                    Object pointValue = ore.getOreValue();
                    for (String index : indices) {
                        int i = Integer.parseInt(index);
                        pointValue = checkPoint(pointValue, i);
                    }
                    this.ore = Ore.newInstance(pointValue);
                }
                if (ore.isNull()) {
                    this.explore = false;
                }
            }
        }

        private Ore extract(Ore preOre, String order) {
            if (preOre == null || preOre.isNull()) {
                return Ore.newInstance(null);
            } else if (preOre.isArray()) {
                Object preOreValue = preOre.getOreValue();
                Object[] preOreValueArr = ((Collection<?>) preOreValue).toArray();
                if (preOreValueArr.length == 0) {
                    return Ore.newInstance(null);
                } else {
                    List<Object> oreValues = Arrays.stream(preOreValueArr)
                            .map(v -> {
                                Map<String, Object> preOreValueElement = toMap(v);
                                if (preOreValueElement == null) {
                                    return null;
                                } else {
                                    return preOreValueElement.get(order);
                                }
                            }).collect(Collectors.toList());
                    if (!arraySaveNull) {
                        oreValues.removeIf(Objects::isNull);
                    }
                    return Ore.newInstance(oreValues);
                }
            } else {
                Map<String, Object> preOreMap = toMap(preOre.getOreValue());
                if (preOreMap == null) {
                    return Ore.newInstance(null);
                } else {
                    Object oreValue = preOreMap.get(order);
                    return Ore.newInstance(oreValue);
                }
            }
        }

        private Object checkPoint(Object value, int index) {
            if (value instanceof Collection) {
                Object[] values = ((Collection<?>) value).toArray();
                return values[index];
            } else {
                throw new IllegalArgumentException("值类型不是数组, 不能使用索引获取");
            }
        }

        @Override
        public String toString() {
            return "Platform{" +
                    "order='" + order + '\'' +
                    ", ore=" + ore +
                    ", explore=" + explore +
                    '}';
        }
    }
}
