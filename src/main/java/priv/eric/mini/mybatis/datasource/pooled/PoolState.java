package priv.eric.mini.mybatis.datasource.pooled;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 连接池状态
 *
 * @author EricTowns
 * @date 2023/3/8 23:45
 */
public class PoolState {

    protected PooledDatasource pooledDatasource;
    /**
     * 空闲连接
     */
    protected final List<PooledConnection> idleConnections = new ArrayList<>();
    /**
     * 活跃连接
     */
    protected final List<PooledConnection> activeConnections = new ArrayList<>();
    /**
     * 请求数
     */
    protected long requestCount = 0L;
    /**
     * 累计请求时间
     */
    protected long accumulatedRequestTime = 0L;
    /**
     * 累计检测时间
     */
    protected long accumulatedCheckoutTime = 0L;

    /**
     * 累计等待时间
     */
    protected long accumulatedWaitTime = 0L;


    public PoolState(PooledDatasource pooledDatasource) {
        this.pooledDatasource = pooledDatasource;
    }

}
