package priv.eric.mini.mybatis.test.ths.miner2;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/3/20 16:02
 */
public class Mine {

    private Object ore;
    private ObjectWrapper oreWrapper;

    public Mine(Object object) {
        this.ore = object;
        this.oreWrapper = new ObjectWrapper(ore);
    }

    public Object getValue(String name) {
        Tokenizer tokenizer = new Tokenizer(name);
        if (tokenizer.hasNext()) {
            // TODO: 2023/3/20
            return null;
        } else {
            return oreWrapper.get(tokenizer);
        }
    }

    private Mine ofProperty(String name) {
        Object value = getValue(name);
        return new Mine(value);
    }

}
