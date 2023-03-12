package priv.eric.mini.mybatis.datasource.unpooled;

import priv.eric.mini.mybatis.datasource.DatasourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description: 无池化数据源工厂
 *
 * @author EricTowns
 * @date 2023/3/12 22:28
 */
public class UnPooledDatasourceFactory implements DatasourceFactory {

    protected Properties properties;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource getDatasource() {
        UnPooledDatasource unPooledDatasource = new UnPooledDatasource();
        unPooledDatasource.setDriver(properties.getProperty("driver"));
        unPooledDatasource.setUrl(properties.getProperty("url"));
        unPooledDatasource.setUsername(properties.getProperty("username"));
        unPooledDatasource.setPassword(properties.getProperty("password"));
        return unPooledDatasource;
    }
}
