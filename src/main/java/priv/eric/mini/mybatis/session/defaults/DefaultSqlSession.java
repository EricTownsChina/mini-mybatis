package priv.eric.mini.mybatis.session.defaults;

import priv.eric.mini.mybatis.session.SqlSession;
import priv.eric.mini.mybatis.session.Configuration;

/**
 * Description: 默认SqlSession实现
 *
 * @author EricTowns
 * @date 2023/2/6 23:39
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) "你被代理了";
    }

    @Override
    public <T> T getMapper(Class<T> mapperInterface) {
        return configuration.getMapper(mapperInterface, this);
    }
}
