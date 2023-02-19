package priv.eric.mini.mybatis.builder;

import priv.eric.mini.mybatis.session.Configuration;

/**
 * Description: 构建器基类
 *
 * @author EricTowns
 * @date 2023/2/19 21:37
 */
public abstract class BaseBuilder {

    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
