package priv.eric.mini.mybatis.session;

/**
 * Description: SqlSession
 *
 * @author EricTowns
 * @date 2023/2/5 21:57
 */
public interface SqlSession {

    /**
     * 查询一条记录
     *
     * @param statement sqlID
     * @param <T>       查询结果类型
     * @return 查询结果
     */
    <T> T selectOne(String statement);

    /**
     * 获取mapper映射器(MapperProxyFactory)
     *
     * @param mapperInterface mapper接口类型
     * @param <T>             映射器类型
     * @return mapper映射器
     */
    <T> T getMapper(Class<T> mapperInterface);

}
