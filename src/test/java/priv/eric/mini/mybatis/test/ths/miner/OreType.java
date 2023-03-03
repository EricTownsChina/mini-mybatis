package priv.eric.mini.mybatis.test.ths.miner;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Description: 对象类型
 *
 * @author EricTowns
 * @date 2023/3/3 17:14
 */
public enum OreType {

    OBJECT,
    ARRAY,
    NULL;

    static final Map<String, OreType> ORE_TYPE_LOOKUP = new HashMap<>(3);

    public static OreType lookup(String type) {
        if (Objects.nonNull(type)) {
            String oreType = type.toUpperCase(Locale.ENGLISH);
            return ORE_TYPE_LOOKUP.get(oreType);
        }
        return NULL;
    }

}
