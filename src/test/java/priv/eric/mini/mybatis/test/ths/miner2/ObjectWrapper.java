package priv.eric.mini.mybatis.test.ths.miner2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author EricTowns
 * @date 2023/3/20 14:52
 */
public class ObjectWrapper {

    public static final ObjectWrapper NULL_OBJECT_WRAPPER = new ObjectWrapper(null);

    private Object ore;

    public ObjectWrapper(Object ore) {
        this.ore = ore;
    }

    private ObjectWrapper ofProperty(String name) {
        Object value = getValue(name);
        if (value == null) {
            return NULL_OBJECT_WRAPPER;
        } else {
            return new ObjectWrapper(value);
        }
    }

    public Object getValue(String name) {
        Tokenizer tokenizer = new Tokenizer(name);
        if (tokenizer.hasNext()) {
            ObjectWrapper objectWrapper = ofProperty(tokenizer.getIndexName());
            if (objectWrapper == NULL_OBJECT_WRAPPER) {
                return null;
            } else {
                return objectWrapper.getValue(tokenizer.getChildren());
            }
        } else {
            return get(tokenizer);
        }
    }

    public Object get(Tokenizer property) {
        if (property.getIndex() != null) {
            Object collectionObject = resolveCollection(property, ore);
            return getCollectionValue(property, collectionObject);
        } else {
            return getBeanProperty(property, ore);
        }
    }

    // TODO: 2023/3/20
    public Object getBeanProperty(Tokenizer property, Object object) {
        return null;
    }

    private Object resolveCollection(Tokenizer property, Object object) {
        if ("".equals(property.getName())) {
            return object;
        } else {
            ObjectWrapper objectWrapper = new ObjectWrapper(object);
            return objectWrapper.getValue(property.getName());
        }
    }

    private Object getCollectionValue(Tokenizer property, Object collectionObject) {
        if (collectionObject instanceof Map) {
            return ((Map<?, ?>) collectionObject).get(property.getIndex());
        } else {
            int index = Integer.parseInt(property.getIndex());
            if (collectionObject instanceof List) {
                return ((List<?>) collectionObject).get(index);
            } else if (collectionObject instanceof Object[]) {
                return ((Object[]) collectionObject)[index];
            } else if (collectionObject instanceof char[]) {
                return ((char[]) collectionObject)[index];
            } else if (collectionObject instanceof boolean[]) {
                return ((boolean[]) collectionObject)[index];
            } else if (collectionObject instanceof int[]) {
                return ((int[]) collectionObject)[index];
            } else if (collectionObject instanceof float[]) {
                return ((float[]) collectionObject)[index];
            } else if (collectionObject instanceof double[]) {
                return ((double[]) collectionObject)[index];
            } else if (collectionObject instanceof short[]) {
                return ((short[]) collectionObject)[index];
            } else if (collectionObject instanceof byte[]) {
                return ((byte[]) collectionObject)[index];
            } else if (collectionObject instanceof long[]) {
                return ((long[]) collectionObject)[index];
            } else {
                throw new IllegalArgumentException("The '" + property.getName() + "' property of " + collectionObject + " is not a List or Array.");
            }
        }
    }

}
