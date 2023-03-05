package priv.eric.mini.mybatis.test.ths.miner;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 工作台
 *
 * @author EricTowns
 * @date 2023/3/3 17:27
 */
public class Workbench {

    private Ore preOre;

    private String order;

    private Ore ore;

    private String message;

    private Workbench() {
    }

    private Workbench(Ore preOre, String order) {
        this.preOre = preOre;
        this.order = order;
    }

    public static Workbench init(Ore stone, String order) {
        return new Workbench(stone, order);
    }

    public void explore() {
        if (Objects.isNull(preOre) || preOre.isNull()) {
            this.ore = Ore.emptyOre();
            this.message = "pre ore is empty";
        } else if (Objects.isNull(order) || order.isEmpty()) {
            this.ore = Ore.emptyOre();
            this.message = "order is empty";
        } else {
            String[] process = read();
            String processOrder = process[0];
            Object value = roughing(processOrder);
            for (int i = 1; i < process.length; i++) {
                processOrder = process[i];
                value = deep(processOrder, value);
            }
            this.ore = Ore.of(value);
            this.message = "job done.";
        }
    }

    private Object roughing(String processOrder) {
        Object preValue = preOre.getValue();
        if (preOre.isObject()) {
            Map<String, Object> valueMap = Toolbox.toMap(preValue);
            return valueMap.get(processOrder);
        } else {
            List<Map<String, Object>> valueMaps = Toolbox.toList(preValue);
            return valueMaps.stream().map(v -> v.get(processOrder)).collect(Collectors.toList());
        }
    }

    private Object deep(String deepOrder, Object value) {
        if (Objects.isNull(value) || !(value instanceof Collection)) {
            this.message = "pre value is null or not a collection!";
            throw new IllegalArgumentException(this.message);
        }

        try {
            int index = Integer.parseInt(deepOrder);
            Collection<?> collectionObj = (Collection<?>) value;
            Object[] objects = collectionObj.toArray();
            if (index < 0) {
                // 小于0为倒数
                return objects[objects.length + index];
            } else {
                return objects[index];
            }
        } catch (NumberFormatException e) {
            this.message = String.format("index not number: [%s]", deepOrder);
            throw new NumberFormatException(this.message);
        }
    }

    private String[] read() {
        StringBuilder processBuilder = new StringBuilder();
        for (int i = 0; i < order.length(); i++) {
            char c = order.charAt(i);
            if ('[' == c) {
                processBuilder.append('.');
            } else if (']' != c) {
                processBuilder.append(c);
            }
        }
        return processBuilder.toString().split("\\.");
    }

    public Ore getPreOre() {
        return preOre;
    }

    public String getOrder() {
        return order;
    }

    public Ore getOre() {
        return ore;
    }

    public String getMessage() {
        return message;
    }
}
