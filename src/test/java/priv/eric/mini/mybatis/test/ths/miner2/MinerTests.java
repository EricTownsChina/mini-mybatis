package priv.eric.mini.mybatis.test.ths.miner2;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Desc:
 *
 * @author EricTownsChina@outlook.com
 * create 2023/4/18 21:15
 */
public class MinerTests {

    @Test
    public void getValue() {
        List<String> oreList = Stream.<String>of("1", "3", "2", "5").collect(Collectors.toList());
        Object value = Miner.instance(oreList).getValue("[1]");

        System.out.println(value);
    }
}
