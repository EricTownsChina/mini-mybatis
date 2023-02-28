package priv.eric.mini.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: 事务接口
 *
 * @author EricTowns
 * @date 2023/2/28 22:31
 */
public interface Transaction {

    /**
     * 获取连接
     *
     * @return 连接
     * @throws SQLException sqlException
     */
    Connection getConnection() throws SQLException;

    /**
     * 提交事务
     *
     * @throws SQLException sqlException
     */
    void commit() throws SQLException;

    /**
     * 回滚
     *
     * @throws SQLException sqlException
     */
    void rollback() throws SQLException;

    /**
     * 关闭事务
     *
     * @throws SQLException sqlException
     */
    void close() throws SQLException;
}
