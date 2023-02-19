package priv.eric.mini.mybatis.mapping;

import priv.eric.mini.mybatis.session.Configuration;

import java.util.Map;

/**
 * Description: mapper对象的语句类
 *
 * @author EricTowns
 * @date 2023/2/16 00:10
 */
public class MappedStatement {

    private String id;
    private Configuration configuration;

    private SqlCommandType sqlCommandType;
    private String sql;
    private String parameterType;
    private String resultType;
    private Map<Integer, String> parameter;

    private MappedStatement() {}

    public static class Builder {
        private final MappedStatement mappedStatement = new MappedStatement();

        public Builder(String id, Configuration configuration, SqlCommandType sqlCommandType, String sql, String parameterType, String resultType, Map<Integer, String> parameter) {
            mappedStatement.id = id;
            mappedStatement.configuration = configuration;
            mappedStatement.sqlCommandType = sqlCommandType;
            mappedStatement.sql = sql;
            mappedStatement.parameterType = parameterType;
            mappedStatement.resultType = resultType;
            mappedStatement.parameter = parameter;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Map<Integer, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<Integer, String> parameter) {
        this.parameter = parameter;
    }
}
