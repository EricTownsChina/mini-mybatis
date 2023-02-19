package priv.eric.mini.mybatis.mapping;

/**
 * Description: SQL指令操作类型
 *
 * @author EricTowns
 * @date 2023/2/16 00:02
 */
public enum SqlCommandType {

    /**
     * 未知
     */
    UNKNOWN,
    /**
     * 查询
     */
    SELECT,
    /**
     * 插入
     */
    INSERT,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE

}
