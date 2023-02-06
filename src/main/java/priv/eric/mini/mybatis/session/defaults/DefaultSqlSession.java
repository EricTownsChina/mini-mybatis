package priv.eric.mini.mybatis.session.defaults;

import priv.eric.mini.mybatis.binding.MapperRegister;
import priv.eric.mini.mybatis.session.SqlSession;

/**
 * Description: 默认SqlSession实现
 *
 * @author EricTowns
 * @date 2023/2/6 23:39
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private final MapperRegister mapperRegister;

    public DefaultSqlSession(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) "你被代理了";
    }

    @Override
    public <T> T getMapper(Class<T> mapperInterface) {
        return mapperRegister.getMapper(mapperInterface, this);
    }
}
