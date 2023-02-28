package priv.eric.mini.mybatis.transaction;

import priv.eric.mini.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Description: 事务工厂
 *
 * @author EricTowns
 * @date 2023/2/28 23:14
 */
public interface TransactionFactory {

    /**
     * 创建一个新的事物
     *
     * @param connection 连接
     * @return
     */
    Transaction newTransaction(Connection connection);

    /**
     * 创建一个新的事物
     *
     * @param dataSource 连接池
     * @param level      事务隔离级别
     * @param autoCommit 是否开启手动事务
     * @return
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
