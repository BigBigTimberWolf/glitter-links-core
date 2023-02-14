package org.glitter.links;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
public interface Values {

    /**
     * 获取全部值
     *
     * @return 全部值
     */
    Map<String, Value> getAllValues();

    /**
     * 获取单个值
     *
     * @param key key
     * @return Optional包装的值
     */
    Optional<Value> getValue(String key);

    /**
     * 将当前的值与指定的值进行合并，并返回新的值
     *
     * @param source 要合并的源
     * @return 新的值
     */
    Values merge(Values source);

    /**
     * 值数量
     *
     * @return size
     */
    int size();


    /**
     * 是否为空
     *
     * @return true 为空，false 非空
     */
    default boolean isEmpty() {
        return size() == 0;
    }


    default String getString(String key, Supplier<String> defaultValue) {
        return getValue(key).map(Value::asString).orElseGet(defaultValue);
    }


}
