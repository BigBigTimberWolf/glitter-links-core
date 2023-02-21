package org.glitter.links.event;

import lombok.Getter;
import lombok.Setter;
import org.glitter.links.Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */


public class Event implements Comparable<Event> {
    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String deviceId;
    @Setter
    @Getter
    private String propertyName;
    @Setter
    @Getter
    private EventType type;
    @Setter
    @Getter
    private Integer order;

    @Getter
    @Setter
    private Value value;
    @Getter
    private final Map<String, Map<String,Trigger>> trigger = new ConcurrentHashMap<>(64);

    @Getter
    @Setter
    private Boolean execStatus = true;


    public Set<Trigger> getAllTrigger(){
        Set<Trigger> triggers = new HashSet<>();
        Collection<Map<String, Trigger>> values = this.trigger.values();
        for (Map<String, Trigger> value : values) {
            triggers.addAll(value.values());
        }
        return triggers;
    }


    public void addTrigger(String deviceId,String propertyName,Trigger trigger){
        if (!this.trigger.containsKey(deviceId)){
            this.trigger.put(deviceId,new ConcurrentHashMap<>());
        }
        this.trigger.get(deviceId).put(propertyName,trigger);
    }



    @Override
    public int compareTo(Event o) {
        return order.compareTo(o.getOrder());
    }
}
