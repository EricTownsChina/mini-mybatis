package priv.eric.mini.mybatis.datasource.pooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 池化数据源
 *
 * @author EricTowns
 * @date 2023/3/7 23:50
 */
public class PooledDatasource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PooledDatasource.class);

    private final PoolState state = new PoolState(this);

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




}
