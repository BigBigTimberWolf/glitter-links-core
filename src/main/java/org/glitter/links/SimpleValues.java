package org.glitter.links;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/9
 */
public class SimpleValues implements Values{

    private final Map<String,Value> store = new ConcurrentHashMap<>();


    @Override
    public Map<String, Value> getAllValues() {
        return store;
    }

    @Override
    public Optional<Value> getValue(String key) {
        Value orDefault = this.store.getOrDefault(key, null);
        return orDefault == null?Optional.empty():Optional.of(orDefault);
    }

    @Override
    public Values merge(Values source) {
        Map<String, Value> sourceValues = source.getAllValues();
        for (String sourceKey : sourceValues.keySet()) {
            if (store.containsKey(sourceKey)){
                store.get(sourceKey).update(sourceValues.get(sourceKey));
            }else {
                store.put(sourceKey,sourceValues.get(sourceKey));
            }
        }
        return this;
    }

    @Override
    public int size() {
        return 0;
    }
}
