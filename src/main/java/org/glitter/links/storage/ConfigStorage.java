package org.glitter.links.storage;

import org.glitter.links.Value;
import org.glitter.links.Values;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 设备信息存储抽象
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
public interface ConfigStorage {
    /**
     * 获取单个值,如果值不存在,则返回{@link  Mono#empty()},可通过{@link  Mono#switchIfEmpty(Mono)}来处理值不存在的情况.
     *
     * @param key key
     * @return Value
     */
    Mono<Value> getConfig(String key);

    /**
     * 获取多个key对应的值,此方法不会返回{@link Mono#empty()},当值都不存在时，可以通过{@link  Values#isEmpty()}来判断.
     *
     * @param keys keys
     * @return Values
     * @see Values
     */
    Mono<Values> getConfigs(Collection<String> keys);

    /**
     * 获取多个值,参照{@link ConfigStorage#getConfigs(Collection)}
     *
     * @param keys keys
     * @return 多个值信息
     */
    default Mono<Values> getConfigs(String... keys) {
        return getConfigs(Arrays.asList(keys));
    }

    /**
     * 设置多个值到配置中,Map中的value应该为可序列化的对象,最好为基本数据类型,字符串类型.
     *
     * @param values 多个值
     * @return 是否成功
     */
    Mono<Boolean> setConfigs(Map<String, Object> values);

    /**
     * 设置单个配置,如果值已经存在则会被覆盖.value应该为可序列化的对象,最好为基本数据类型,字符串类型.
     *
     * @param key   key
     * @param value 值
     * @return 是否成功
     */
    Mono<Value> setConfig(String key, Value value);

    /**
     * 清空全部配置
     * @return 是否成功
     */
    Mono<Boolean> clear();


}
