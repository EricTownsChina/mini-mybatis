package priv.eric.mini.mybatis.test.ths.miner2;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/3/20 16:02
 */
public class Miner {

    public static Builder instance(Object ore) {
        return new Builder(ore);
    }

    public static class Builder {
        private Object ore;

        public Builder(Object ore) {
            this.ore = ore;
        }

        public Builder setOre(Object ore) {
            this.ore = ore;
            return this;
        }

        public Object getValue(String order) {
            ObjectWrapper objectWrapper = new ObjectWrapper(ore);
            return objectWrapper.getValue(order);
        }
    }

}
