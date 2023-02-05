package priv.eric.mini.mybatis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Description: Mapper接口的代码类
 *
 * @author EricTowns
 * @date 2023/2/5 15:07
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -2936274246541566118L;

    /**
     * 需要被代理的mapper接口
     */
    private final Class<T> mapperInterface;
    /**
     * sqlSession
     */
    private final Map<String, String> sqlSession;

    public MapperProxy(Class<T> mapperInterface, Map<String, String> sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            // Object类定义的方法不需要被代理
            return method.invoke(proxy, args);
        } else {
            return "你被代理了, " + sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }

}
