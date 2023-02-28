package priv.eric.mini.mybatis.transaction.jdbc;

import priv.eric.mini.mybatis.session.TransactionIsolationLevel;
import priv.eric.mini.mybatis.transaction.Transaction;
import priv.eric.mini.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Description: Jdbc事务 工厂
 *
 * @author EricTowns
 * @date 2023/2/28 23:17
 */
public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(Connection connection) {
        return new JdbcTransaction(connection);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}
