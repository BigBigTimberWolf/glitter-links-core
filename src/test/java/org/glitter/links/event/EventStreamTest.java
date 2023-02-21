package org.glitter.links.event;


import lombok.extern.slf4j.Slf4j;
import org.glitter.links.SimpleValue;
import org.glitter.links.SimpleValues;
import org.glitter.links.event.trigger.EqualTrigger;
import reactor.core.publisher.Flux;

import java.util.concurrent.ConcurrentHashMap;
@Slf4j
class EventStreamTest {

    public static void main(String[] args) {
        EventStream eventStream = new EventStream();
        eventStream.setEventStore(buildEvent2());
        eventStream.setEventStore(buildEvent());
        SimpleValues simpleValues = new SimpleValues();
        simpleValues.getAllValues().put("testProperty111",new SimpleValue("1"));
        simpleValues.getAllValues().put("testProperty2",new SimpleValue("1"));
        eventStream.touch("test",simpleValues)
                .subscribe(event -> log.info("执行:{}",event.getId()));
    }

    private static Event buildEvent2(){
        Event event = new Event();
        event.setId("2");
        event.setType(EventType.SET);
        event.setOrder(2);
        event.addTrigger("test","testProperty2",buildTrigger());
        event.setPropertyName("tttexec");
        event.setDeviceId("2222");
        return event;
    }
    private static Event buildEvent(){
        Event event = new Event();
        event.setId("1");
        event.setType(EventType.SET);
        event.setOrder(1);
        event.addTrigger("test","testProperty",buildTrigger());
        event.addTrigger("test","testProperty2",buildTrigger());
        event.setPropertyName("tttexec");
        event.setDeviceId("2222");
        return event;
    }

    private static Trigger buildTrigger(){
        EqualTrigger equalTrigger = new EqualTrigger(true);
        equalTrigger.setValue("1");
        return equalTrigger;
    }

}