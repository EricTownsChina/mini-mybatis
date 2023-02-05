package priv.eric.mini.mybatis.binding;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.eric.mini.mybatis.dao.IUserDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 测试类
 *
 * @author EricTowns
 * @date 2023/2/5 15:56
 */
public class MapperProxyFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperProxyFactoryTest.class);

    @Test
    public void instance() {
        MapperProxyFactory<IUserDao> proxyFactory = new MapperProxyFactory<>(IUserDao.class);

        Map<String, String> sqlSession = new HashMap<>(2);
        sqlSession.put("priv.eric.mini.mybatis.dao.IUserDao.queryName", "执行sql语句, 查询用户姓名");

        IUserDao userDao = proxyFactory.newInstance(sqlSession);
        String userName = userDao.queryName("1111");
        LOGGER.info("查询结果: {}", userName);
    }
}