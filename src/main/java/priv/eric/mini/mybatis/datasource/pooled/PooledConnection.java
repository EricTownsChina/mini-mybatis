package priv.eric.mini.mybatis.datasource.pooled;

import priv.eric.mini.mybatis.datasource.unpooled.UnPooledDatasource;

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

    public PooledConnection(Connection connection, PooledDatasource datasource) {
        this.hashCode = connection.hashCode();
        this.realConnection = connection;
        this.datasource = datasource;
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

    public int getHashCode() {
        return realConnection == null ? 0 : realConnection.hashCode();
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public void setProxyConnection(Connection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getLastUsedTimestamp() {
        return lastUsedTimestamp;
    }

    public void setLastUsedTimestamp(long lastUsedTimestamp) {
        this.lastUsedTimestamp = lastUsedTimestamp;
    }

    public boolean isValid() {
        return valid && realConnection != null && datasource.pingConnection(this);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
