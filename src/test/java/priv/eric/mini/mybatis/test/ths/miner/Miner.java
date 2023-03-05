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
    private Workbench workbench;

    private String order;

    private Object mine;

    public Miner(Object mine, String order) {
        this.mine = mine;
        this.order = order;
        this.workbench = Workbench.init(Ore.of(mine), order);
    }
}
