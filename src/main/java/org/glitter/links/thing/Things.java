package org.glitter.links.thing;

import com.hazelcast.core.HazelcastInstance;
import lombok.Getter;
import org.glitter.links.Value;
import org.glitter.links.Values;
import org.glitter.links.storage.HazelcastStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/9
 */
@Component
public class Things {

    private final Map<String, Things> store = new ConcurrentHashMap<>();


    Map<String, Things> getAllThings(){
        return store;
    };

    @Autowired
    HazelcastInstance hazelcastInstance;



}
