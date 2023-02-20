package org.glitter.links.event;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.glitter.links.Value;
import org.glitter.links.Values;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */
@Slf4j
@Component
public class EventStream {

    // <设备id,<测点,List(事件)>>
    private final Map<String, Map<String, List<Event>>> eventListen = new ConcurrentHashMap<>(4096);

    /**
     * 联动列表
     */
    private final Map<String, Event> eventStore = new ConcurrentHashMap<>(4096);

    public Flux<Event> touch(String deviceId, Values values) {
        if (!eventListen.containsKey(deviceId)) {
            return Flux.empty();
        }
        Map<String, Value> propertyValueMap = values.getAllValues();
        for (String propertyName : propertyValueMap.keySet()) {
            touch(deviceId, propertyName, propertyValueMap.get(propertyName));
        }
        /*判断是否可以执行*/
        // 获取判断范围
        Set<String> propertyNames = propertyValueMap.keySet();
        HashSet<Event> execEvents = new HashSet<>();
        for (String propertyName : propertyNames) {
            List<Event> events = eventListen.get(deviceId).get(propertyName);
            execEvents.addAll(events);
        }
        // 获取判断范围下的所有触发器
        Set<Event> finalExecEventSet = new HashSet<>();
        for (Event execEvent : execEvents) {
            Set<Trigger> allTrigger = execEvent.getAllTrigger();
            long mustNumber = allTrigger.stream().filter(Trigger::must).count();

            long fMust = allTrigger.stream().filter(Trigger::must) // 必须满足的
                    .filter(Trigger::execStatus)
                    .filter(Trigger::getStatus)
                    .count();
            if (mustNumber != 0 && fMust != mustNumber) {
                continue;
            }

            long tNMust = allTrigger.stream().filter(trigger -> !trigger.must())
                    .filter(Trigger::execStatus)
                    .filter(Trigger::getStatus)
                    .count();
            if (allTrigger.stream().anyMatch(trigger -> !trigger.must()) && tNMust == 0) {
                continue;
            }
            finalExecEventSet.add(execEvent);
        }

        List<Event> events = new ArrayList<>(finalExecEventSet.stream().toList());
        Collections.sort(events);
        return Flux.fromStream(events.stream());


    }

    public void touch(String deviceId, String propertyName, Value value) {
        if (!eventListen.containsKey(deviceId)) {
            return;
        }
        if (!eventListen.get(deviceId).containsKey(propertyName)) {
            return;
        }
        List<Event> events = eventListen.get(deviceId).get(propertyName);
        for (Event event : events) {
            Trigger trigger = event.getTrigger().get(deviceId).get(propertyName);
            trigger.check(value);
        }
    }

    public void setEventStore(Event event) {
        eventStore.put(event.getId(), event);
        Map<String, Map<String, Trigger>> trigger = event.getTrigger();
        if (trigger.isEmpty()) {
            return;
        }
        Set<String> deviceIds = trigger.keySet();
        for (String deviceId : deviceIds) {
            Map<String, Trigger> properNameMap = trigger.get(deviceId);
            if (properNameMap.isEmpty()) {
                return;
            }
            for (String propertyName : properNameMap.keySet()) {
                setEventListen(deviceId, propertyName, event.getId());
            }
        }
    }

    public void setEventListen(String deviceId, String propertyName, String eventId) {
        if (!eventStore.containsKey(eventId)) {
            throw new RuntimeException(StrUtil.format("不存在的事件id:{}", eventId));
        }
        if (!eventListen.containsKey(deviceId)) {
            eventListen.put(deviceId, new ConcurrentHashMap<>(128));
        }
        if (!eventListen.get(deviceId).containsKey(propertyName)) {
            eventListen.get(deviceId).put(propertyName, new CopyOnWriteArrayList<>());
        }
        List<Event> events = eventListen.get(deviceId).get(propertyName);
        events.add(eventStore.get(eventId));
        Collections.sort(events);
    }


}
