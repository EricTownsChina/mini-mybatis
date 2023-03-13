package priv.eric.mini.mybatis.datasource.pooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.eric.mini.mybatis.datasource.unpooled.UnPooledDatasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Description: 池化数据源
 *
 * @author EricTowns
 * @date 2023/3/7 23:50
 */
public class PooledDatasource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PooledDatasource.class);

    private final PoolState state = new PoolState(this);

    private final UnPooledDatasource datasource;

    /**
     * 最大活跃连接数
     */
    protected int maximumActiveConnections = 10;
    /**
     * 最大空闲连接数
     */
    protected int maximumIdleConnections = 5;
    /**
     * 最大checkout时间
     */
    protected int maximumCheckoutTime = 20000;
    /**
     * 是否开启连接侦测
     */
    protected boolean poolPingEnabled = false;
    /**
     * 侦测查询语句, 会让数据报错
     */
    protected String poolPingQuery = "NO PING QUERY SET";

    public PooledDatasource() {
        this.datasource = new UnPooledDatasource();
    }

    protected void pushConnection(PooledConnection connection) throws SQLException {
        synchronized (state) {
            state.activeConnections.remove(connection);
            if (connection.isValid()) {

            }
        }
    }

    protected boolean pingConnection(PooledConnection connection) {
        boolean result = true;
        try {
            result = !connection.getRealConnection().isClosed();
        } catch (SQLException e) {
            LOGGER.info("connection " + connection.getHashCode() + " is bad: {}", e.getMessage());
            result = false;
        }

        if (result) {
            if (poolPingEnabled) {
                try {
                    LOGGER.info("test connection " + connection.getHashCode() + ".....");
                    Connection realConnection = connection.getRealConnection();
                    Statement statement = realConnection.createStatement();
                    ResultSet resultSet = statement.executeQuery(poolPingQuery);
                    resultSet.close();
                    if (!realConnection.getAutoCommit()) {
                        realConnection.rollback();
                    }
                    result = true;
                    LOGGER.info("connection {} is good!", connection.getHashCode());
                } catch (Exception e) {
                    LOGGER.info("ping query {} failed {}", poolPingQuery, e.getMessage());
                    try {
                        Connection realConnection = connection.getRealConnection();
                        realConnection.close();
                    } catch (SQLException ignore) {
                    }
                    result = false;
                    LOGGER.info("connection {} is bad: {}", connection.getRealConnection(), e.getMessage());
                }
            }
        }
        return result;
    }
}
