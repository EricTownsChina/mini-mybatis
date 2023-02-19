package priv.eric.mini.mybatis.test.dao;

import priv.eric.mini.mybatis.test.po.User;

/**
 * Description: 测试userMapper
 *
 * @author EricTowns
 * @date 2023/2/5 15:57
 */
public interface IUserDao {

    String queryName(String userId);

    Integer queryAge(String userId);

    User queryUserInfoById(Long id);

}
