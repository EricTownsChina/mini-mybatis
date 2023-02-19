package priv.eric.mini.mybatis.session.defaults;

import priv.eric.mini.mybatis.session.Configuration;
import priv.eric.mini.mybatis.session.SqlSession;
import priv.eric.mini.mybatis.session.SqlSessionFactory;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/2/7 00:45
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

}
