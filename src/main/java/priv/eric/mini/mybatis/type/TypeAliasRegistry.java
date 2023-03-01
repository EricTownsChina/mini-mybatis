package priv.eric.mini.mybatis.type;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description: 类型别名注册机
 *
 * @author EricTowns
 * @date 2023/3/1 23:15
 */
public class TypeAliasRegistry {

    private final Map<String, Class<?>> TYPE_ALIASES = new HashMap<>(8);

    public TypeAliasRegistry() {
        registerAlias("string", String.class);

        registerAlias("byte", Byte.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("long", Long.class);
        registerAlias("float", Float.class);
        registerAlias("double", Double.class);
        registerAlias("boolean", Boolean.class);
        registerAlias("integer", Integer.class);
    }

    public void registerAlias(String alias, Class<?> value) {
        if (ObjectUtils.isEmpty(alias)) {
            return;
        }
        String key = alias.toLowerCase(Locale.ENGLISH);
        TYPE_ALIASES.put(key, value);
    }

    public <T> Class<T> resolveAlias(String alias) {
        String key = alias.toLowerCase(Locale.ENGLISH);
        return (Class<T>) TYPE_ALIASES.get(alias);
    }

}
