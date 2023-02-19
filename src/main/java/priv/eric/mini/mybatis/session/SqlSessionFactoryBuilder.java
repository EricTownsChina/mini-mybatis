package priv.eric.mini.mybatis.session;

import priv.eric.mini.mybatis.builder.xml.XmlConfigBuilder;
import priv.eric.mini.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * Description: SqlSessionFactory构建器
 *
 * @author EricTowns
 * @date 2023/2/19 23:07
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }


}
