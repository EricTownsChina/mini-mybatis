package priv.eric.mini.mybatis.test.ths.miner;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(MinerTests.class);

    public static void main(String[] args) {
//        Miner miner = Miner.explore(testObj(), ".s33[2]");
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

    @Test
    public void stringTest() {
        String order = ".field1.field2";
        String[] split = order.split("\\.");
        LOGGER.info("{}", Arrays.toString(split));

        LOGGER.info("{}", order.startsWith("."));
    }


}
