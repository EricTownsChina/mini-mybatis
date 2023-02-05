package priv.eric.mini.mybatis.binding;

import priv.eric.mini.mybatis.common.BeanUtil;
import priv.eric.mini.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Description: mapper代理工厂
 *
 * @author EricTowns
 * @date 2023/2/5 15:37
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        return BeanUtil.cast(
                Proxy.newProxyInstance(
                        mapperInterface.getClassLoader(),
                        new Class[]{mapperInterface},
                        new MapperProxy<>(mapperInterface, sqlSession)),
                mapperInterface);
    }

}
