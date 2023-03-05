package priv.eric.mini.mybatis.test.ths.miner;

import java.util.*;

/**
 * Description: 对象内属性获取器
 *
 * @author EricTowns
 * @date 2023/2/12 00:26
 */
public class Miner {
    private final Workbench workbench;

    private final String order;

    private final Object mine;

    private final List<Workbench> history;

    public Miner(Object mine, String order) {
        this.mine = mine;
        this.order = order;
        this.workbench = Workbench.init(Ore.of(mine), order);
        this.history = new ArrayList<>();
    }

    public static Miner newInstance(Object mine, String order) {
        return new Miner(mine, order);
    }

    public Workbench getWorkbench() {
        return workbench;
    }

    public String getOrder() {
        return order;
    }

    public Object getMine() {
        return mine;
    }

    public List<Workbench> history() {
        return this.history;
    }

    public Object explore() {
        String[] subOrders = subOrder();
        Workbench exploreWorkbench = workbench;
        Ore preOre = workbench.getPreOre();
        for (String subOrder : subOrders) {
            exploreWorkbench = Workbench.init(preOre, subOrder);
            exploreWorkbench.explore();
            preOre = exploreWorkbench.getOre();
            history.add(exploreWorkbench);
        }
        return exploreWorkbench.getOre().getValue();
    }

    private String[] subOrder() {
        String[] subOrderArr = order.split("\\.");
        return Arrays.copyOfRange(subOrderArr, 1, subOrderArr.length);
    }


}
