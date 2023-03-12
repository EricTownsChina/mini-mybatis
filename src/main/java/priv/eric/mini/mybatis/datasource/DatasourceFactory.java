package priv.eric.mini.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description: 数据源工厂
 *
 * @author EricTowns
 * @date 2023/3/12 22:29
 */
public interface DatasourceFactory {

    /**
     * 设置数据源配置
     *
     * @param properties 配置
     */
    void setProperties(Properties properties);

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    DataSource getDatasource();
}
