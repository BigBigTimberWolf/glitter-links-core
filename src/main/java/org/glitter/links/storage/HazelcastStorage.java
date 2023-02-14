package org.glitter.links.storage;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.Getter;
import org.glitter.links.Value;
import org.glitter.links.Values;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
public class HazelcastStorage implements ConfigStorage{



    IMap<String, Cache> cache;

    public HazelcastStorage(HazelcastInstance hazelcastInstance,String deviceId) {
        this.cache = hazelcastInstance.getMap(deviceId);
    }

    @Override
    public Mono<Value> getConfig(String key) {
        return cache.get(key).getRef();
    }


    @Override
    public Mono<Values> getConfigs(Collection<String> keys) {
        Map<String, Cache> all = cache.getAll(new HashSet<>(keys));
        return null;
    }

    @Override
    public Mono<Boolean> setConfigs(Map<String, Object> values) {
        return null;
    }

    @Override
    public Mono<Value> setConfig(String key, Value value) {
        return getCache(key)
                .switchIfEmpty(saveCache(key,value))
                .flatMap(Cache::getRef);
    }
    private Mono<Cache> saveCache(String key,Value value){
        Cache cacheValue = new Cache(value, key);
        cache.set(key,cacheValue);
        return getCache(key);
    }

    private Mono<Cache> getCache(String key){
        return Mono.just(cache.get(key));
    }

    @Override
    public Mono<Boolean> clear() {
        return null;
    }


    static class Cache{
        long cacheTime = System.nanoTime();
        @Getter
        String id;
        volatile Mono<Value> ref;
        volatile Value cached;

        public Cache(Value cached,String id) {
            setCached(cached,id);
        }
        Mono<Value> getRef() {
            return ref;
        }

        public void setCached(Value cached,String id){
            cacheTime = System.nanoTime();
            this.id = id;
            this.cached = cached;
            this.ref = Mono.just(cached);
        }
    }
}
