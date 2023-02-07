package priv.eric.mini.mybatis.binding;

import priv.eric.mini.mybatis.common.BeanUtil;
import priv.eric.mini.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

import static priv.eric.mini.mybatis.common.Constant.INT_8;

/**
 * Description: 映射器(MapperProxyFactory)注册机
 *
 * @author EricTowns
 * @date 2023/2/5 22:13
 */
public class MapperRegister {

    private final Map<Class<?>, MapperProxyFactory<?>> mappers = new HashMap<>(INT_8);

    public MapperRegister() {

    }

    public <T> T getMapper(Class<T> mapperInterface, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = BeanUtil.cast(mappers.get(mapperInterface));
        if (mapperProxyFactory == null) {
            throw new NullPointerException("未找到" + mapperInterface.getSimpleName() + "的映射器.");
        } else {
            return mapperProxyFactory.newInstance(sqlSession);
        }
    }

    public <T> boolean hasMapper(Class<T> mapperInterface) {
        return mappers.containsKey(mapperInterface);
    }

    public <T> void addMapper(Class<T> mapperInterface) {
        if (mapperInterface.isInterface()) {
            if (hasMapper(mapperInterface)) {
                return;
            }
            mappers.put(mapperInterface, new MapperProxyFactory<>(mapperInterface));
        }
    }

    public void addMappers(String packageName) {

    }


}
