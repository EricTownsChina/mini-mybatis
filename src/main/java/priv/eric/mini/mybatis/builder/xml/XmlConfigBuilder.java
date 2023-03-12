package priv.eric.mini.mybatis.builder.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import priv.eric.mini.mybatis.builder.BaseBuilder;
import priv.eric.mini.mybatis.common.Constant;
import priv.eric.mini.mybatis.common.io.Resources;
import priv.eric.mini.mybatis.mapping.MappedStatement;
import priv.eric.mini.mybatis.mapping.SqlCommandType;
import priv.eric.mini.mybatis.session.Configuration;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: XML配置构建器
 *
 * @author EricTowns
 * @date 2023/2/19 21:37
 */
public class XmlConfigBuilder extends BaseBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlConfigBuilder.class);

    private static final Pattern SQL_PARAM_PATTERN = Pattern.compile("(#\\{(.*?)})");

    private Element root;

    public XmlConfigBuilder(Reader reader) {
        super(new Configuration());
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(reader);
            this.root = document.getRootElement();
        } catch (DocumentException e) {
            LOGGER.error("读取XML文件失败: ", e);
        }
    }

    public Configuration parse() {
        try {
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException(String.format("解析mapper文件失败: %s", e.getMessage()), e);
        }
        return this.configuration;
    }

    private void environmentsElement(Element environments) throws Exception {
        String environment = environments.attributeValue("default");
        List<Element> environmentList = environments.elements("environment");
        Element defaultEnvironment = environmentList.stream()
                .filter(e -> environment.equals(e.attributeValue("id")))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);


    }


    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            String namespace = root.attributeValue("namespace");

            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                Map<Integer, String> parameterMap = new HashMap<>();
                Matcher matcher = SQL_PARAM_PATTERN.matcher(sql);
                for (int i = 0; matcher.find(); i++) {
                    String sourcePlaceholder = matcher.group(1);
                    String paramName = matcher.group(2);
                    sql = sql.replace(sourcePlaceholder, "?");
                    parameterMap.put(i, paramName);
                }

                String mappedStatementId = namespace + Constant.DOT + id;
                String nodeName = node.getName();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                MappedStatement mappedStatement = new MappedStatement.Builder(mappedStatementId, this.configuration, sqlCommandType, sql, parameterType, resultType, parameterMap)
                        .build();
                this.configuration.addMappedStatement(mappedStatement);
            }
            configuration.addMapper(Resources.classForName(namespace));
        }

    }
}
