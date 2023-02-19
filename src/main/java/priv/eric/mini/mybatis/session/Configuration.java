package priv.eric.mini.mybatis.session;

import priv.eric.mini.mybatis.binding.MapperRegister;
import priv.eric.mini.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 配置
 *
 * @author EricTowns
 * @date 2023/2/16 00:05
 */
public class Configuration {

    /**
     * 映射注册机
     */
    protected MapperRegister mapperRegister = new MapperRegister(this);

    /**
     * 映射的语句map
     */
    protected Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappers(String packageName) {
        this.mapperRegister.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> mapperInterface) {
        this.mapperRegister.addMapper(mapperInterface);
    }

    public <T> T getMapper(Class<T> mapperInterface, SqlSession sqlSession) {
        return this.mapperRegister.getMapper(mapperInterface, sqlSession);
    }

    public void addMappedStatement(MappedStatement mappedStatement) {
        this.mappedStatements.put(mappedStatement.getId(), mappedStatement);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }

}
