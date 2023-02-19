package priv.eric.mini.mybatis.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.eric.mini.mybatis.common.io.Resources;
import priv.eric.mini.mybatis.test.dao.IUserDao;
import priv.eric.mini.mybatis.session.SqlSession;
import priv.eric.mini.mybatis.session.SqlSessionFactory;
import priv.eric.mini.mybatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Description: 测试类
 *
 * @author EricTowns
 * @date 2023/2/5 15:56
 */
public class MiniMybatisTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniMybatisTests.class);

    @Test
    public void instance() {
//        MapperRegister mapperRegister = new MapperRegister();
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
//        SqlSession defaultSqlSession = sqlSessionFactory.openSession();
//        MapperProxyFactory<IUserDao> proxyFactory = new MapperProxyFactory<>(IUserDao.class);
//
//        IUserDao userDao = proxyFactory.newInstance(defaultSqlSession);
//        String userName = userDao.queryName("1111");
//        LOGGER.info("查询结果: {}", userName);
    }

    @Test
    public void sqlSession() {
//        MapperRegister mapperRegister = new MapperRegister();
//        mapperRegister.addMappers("priv.eric.mini.mybatis.dao");
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
//        SqlSession defaultSqlSession = sqlSessionFactory.openSession();
//
//        IUserDao userDao = defaultSqlSession.getMapper(IUserDao.class);
//        String userName = userDao.queryName("1111");
//        LOGGER.info("查询结果: {}", userName);
    }

    @Test
    public void sqlSessionFactory() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        String qqqq = userDao.queryName("qqqq");
        LOGGER.info("测试结果: {}", qqqq);
    }

}