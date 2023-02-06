package priv.eric.mini.mybatis.session.defaults;

import priv.eric.mini.mybatis.binding.MapperRegister;
import priv.eric.mini.mybatis.session.SqlSession;
import priv.eric.mini.mybatis.session.SqlSessionFactory;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/2/7 00:45
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegister mapperRegister;

    public DefaultSqlSessionFactory(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegister);
    }

}
