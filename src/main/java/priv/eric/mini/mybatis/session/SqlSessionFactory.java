package priv.eric.mini.mybatis.session;

/**
 * Description: SqlSession工厂
 *
 * @author EricTowns
 * @date 2023/2/7 00:43
 */
public interface SqlSessionFactory {

    /**
     * 打开一个SqlSession
     *
     * @return SqlSession
     */
    SqlSession openSession();

}
