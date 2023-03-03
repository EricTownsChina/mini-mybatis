package priv.eric.mini.mybatis.test.ths.miner;

import java.util.Collection;

/**
 * Description: 中间对象
 *
 * @author EricTowns
 * @date 2023/3/3 17:12
 */
public class Ore {

    /**
     * 对象值
     */
    private Object value;
    /**
     * 对象类型
     */
    private OreType type;

    private Ore(Object value) {
        this.value = value;
        if (this.value == null) {
            this.type = OreType.NULL;
        } else if (this.value instanceof Collection) {
            this.type = OreType.ARRAY;
        } else {
            this.type = OreType.OBJECT;
        }
    }

    public static Ore of(Object oreValue) {
        return new Ore(oreValue);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public OreType getType() {
        return type;
    }

    public void setType(OreType type) {
        this.type = type;
    }

    public boolean isNull() {
        return OreType.NULL == this.type;
    }

    public boolean isArray() {
        return OreType.ARRAY == this.type;
    }

    public boolean isObject() {
        return OreType.OBJECT == this.type;
    }

    @Override
    public String toString() {
        return "Ore{" +
                "oreValue=" + value +
                ", oreType='" + type + '\'' +
                '}';
    }

}
