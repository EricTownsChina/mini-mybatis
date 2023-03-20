package priv.eric.mini.mybatis.test.ths.miner2;

import java.util.Iterator;

/**
 * Description: 简单分词器
 *
 * @author EricTowns
 * @date 2023/3/20 14:08
 */
public class Tokenizer implements Iterator<Tokenizer> {

    private final String indexName;
    private final String children;
    private String name;
    private String index;

    public Tokenizer(String fullName) {
        int delimiter = fullName.indexOf(".");
        if (delimiter > -1) {
            name = fullName.substring(0, delimiter);
            children = fullName.substring(delimiter + 1);
        } else {
            name = fullName;
            children = null;
        }
        indexName = name;
        delimiter = name.indexOf("[");
        if (delimiter > -1) {
            index = name.substring(delimiter + 1, name.length() - 1);
            name = name.substring(0, delimiter);
        }
    }

    public String getName() {
        return name;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndex() {
        return index;
    }

    public String getChildren() {
        return children;
    }

    @Override
    public boolean hasNext() {
        return children != null;
    }

    @Override
    public Tokenizer next() {
        return new Tokenizer(children);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove is not supported, it is no meaning.");
    }

}
