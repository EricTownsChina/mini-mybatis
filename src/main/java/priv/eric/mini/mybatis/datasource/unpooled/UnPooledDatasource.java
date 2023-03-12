package priv.eric.mini.mybatis.datasource.unpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 无池化数据源
 *
 * @author EricTowns
 * @date 2023/3/9 00:11
 */
public class UnPooledDatasource implements DataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnPooledDatasource.class);

    private ClassLoader driverClassLoader;

    private Properties driverProperties;

    private static Map<String, Driver> registerDrivers = new ConcurrentHashMap<>();

    private String driver;

    private String url;

    private String username;

    private String password;

    private Boolean autoCommit;

    private Integer defaultTransactionIsolationLevel;

    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            registerDrivers.put(driver.getClass().getName(), driver);
        }
    }

    private synchronized void initDriver() throws SQLException {
        if (!registerDrivers.containsKey(this.driver)) {
            try {
                Class<?> driverType = Class.forName(this.driver, true, this.driverClassLoader);
                Driver driverInstance = (Driver) driverType.newInstance();
                DriverManager.registerDriver(driverInstance);
                registerDrivers.put(this.driver, driverInstance);
            } catch (Exception e) {
                throw new SQLException("error setting driver on unpooled datasource, Cause: " + e);
            }
        }
    }

    private Connection doGetConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        if (this.driverProperties != null) {
            properties.putAll(this.driverProperties);
        }
        if (Objects.nonNull(username)) {
            properties.put("user", username);
        }
        if (Objects.nonNull(password)) {
            properties.put("password", password);
        }
        return doGetConnection(properties);
    }

    private Connection doGetConnection(Properties properties) throws SQLException {
        initDriver();
        Connection connection = DriverManager.getConnection(this.url, properties);
        if (this.autoCommit != null && this.autoCommit != connection.getAutoCommit()) {
            connection.setAutoCommit(this.autoCommit);
        }
        if (this.defaultTransactionIsolationLevel != null) {
            connection.setTransactionIsolation(this.defaultTransactionIsolationLevel);
        }
        return connection;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return doGetConnection(this.username, this.password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException(getClass().getName() + " is not a wrapper.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return java.util.logging.Logger.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    public ClassLoader getDriverClassLoader() {
        return driverClassLoader;
    }

    public void setDriverClassLoader(ClassLoader driverClassLoader) {
        this.driverClassLoader = driverClassLoader;
    }

    public Properties getDriverProperties() {
        return driverProperties;
    }

    public void setDriverProperties(Properties driverProperties) {
        this.driverProperties = driverProperties;
    }

    public static Map<String, Driver> getRegisterDrivers() {
        return registerDrivers;
    }

    public static void setRegisterDrivers(Map<String, Driver> registerDrivers) {
        UnPooledDatasource.registerDrivers = registerDrivers;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public Integer getDefaultTransactionIsolationLevel() {
        return defaultTransactionIsolationLevel;
    }

    public void setDefaultTransactionIsolationLevel(Integer defaultTransactionIsolationLevel) {
        this.defaultTransactionIsolationLevel = defaultTransactionIsolationLevel;
    }
}
