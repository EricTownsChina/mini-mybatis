package priv.eric.mini.mybatis.datasource.pooled;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/3/3 11:30
 */
public class PooledConnection implements InvocationHandler {

    private static final String CLOSE = "close";
    private static final Class<?>[] INTERFACES = new Class<?>[]{Connection.class};

    private int hashCode = 0;

    private PooledDatasource datasource;

    /**
     * 真实的连接
     */
    private Connection realConnection;
    /**
     * 代理的连接
     */
    private Connection proxyConnection;

    private long createdTimestamp;
    private long lastUsedTimestamp;

    private boolean valid;

    public PooledConnection(Connection connection, PooledDatasource pooledDatasource) {
        this.hashCode = connection.hashCode();
        this.realConnection = connection;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), INTERFACES, this);
        long now = System.currentTimeMillis();
        this.createdTimestamp = now;
        this.lastUsedTimestamp = now;
        this.valid = true;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
            // todo 回收连接
            return null;
        } else {
            if (!Object.class.equals(method.getDeclaringClass())) {
                checkConnection();
            }
            return method.invoke(realConnection, args);
        }
    }

    /**
     * 检查连接
     *
     * @throws SQLException SQLException
     */
    private void checkConnection() throws SQLException {
        if (!this.valid) {
            throw new SQLException("Error access PooledConnection, Connection is invalid.");
        }
    }
}
