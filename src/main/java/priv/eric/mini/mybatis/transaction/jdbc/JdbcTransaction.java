package priv.eric.mini.mybatis.transaction.jdbc;

import priv.eric.mini.mybatis.session.TransactionIsolationLevel;
import priv.eric.mini.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: JDBC事务
 *
 * @author EricTowns
 * @date 2023/2/28 22:46
 */
public class JdbcTransaction implements Transaction {

    protected Connection connection;
    protected DataSource dataSource;
    protected TransactionIsolationLevel level = TransactionIsolationLevel.NONE;
    protected boolean autoCommit;

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        this.dataSource = dataSource;
        this.level = level;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        connection = this.dataSource.getConnection();
        connection.setTransactionIsolation(this.level.getLevel());
        connection.setAutoCommit(autoCommit);
        return null;
    }

    @Override
    public void commit() throws SQLException {
        if (this.connection != null && !this.connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (this.connection != null && !this.connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (this.connection != null && !this.connection.getAutoCommit()) {
            connection.close();
        }
    }
}
