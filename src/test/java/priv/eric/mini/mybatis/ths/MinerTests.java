package priv.eric.mini.mybatis.ths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/2/12 23:25
 */
public class MinerTests {

    public static void main(String[] args) {
        Miner miner = Miner.explore(testObj(), ".s33[2]");
        System.out.println(miner.reward());
        System.out.println(miner.explorationDaily());
    }

    public static Object testObj() {
        Map<Object, Object> map = new HashMap<>();
        map.put("sss", "ssss");
        map.put("s111", "ssss1");
        map.put("s33", Arrays.asList(1, "2", 3, 4D));
        map.put("sq", "sss3");
        map.put("s", "sss4");
        return map;
    }


}
