package priv.eric.mini.mybatis.test.ths.miner;

import java.util.Objects;

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



        return null;
    }

    private Object deep(String deepOrder, Object value) {
        return null;
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
