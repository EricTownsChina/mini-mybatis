package priv.eric.mini.mybatis.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: JBDC类型枚举
 *
 * @author EricTowns
 * @date 2023/3/1 23:46
 */
public enum JdbcType {

    /**
     * integer
     */
    INTEGER(Types.INTEGER),
    /**
     * float
     */
    FLOAT(Types.FLOAT),
    /**
     * decimal
     */
    DECIMAL(Types.DECIMAL),
    /**
     * varchar
     */
    VARCHAR(Types.VARCHAR),
    /**
     * timestamp
     */
    TIMESTAMP(Types.TIMESTAMP),
    /**
     * double
     */
    DOUBLE(Types.DOUBLE);

    /**
     * 类型code
     */
    private final int typeCode;

    private static final Map<Integer, JdbcType> CODE_LOOKUP
            = new HashMap<>(6);

    static {
        for (JdbcType type : JdbcType.values()) {
            CODE_LOOKUP.put(type.typeCode, type);
        }
    }

    JdbcType(int typeCode) {
        this.typeCode = typeCode;
    }

    public static JdbcType ofCode(int code) {
        return CODE_LOOKUP.get(code);
    }
}
