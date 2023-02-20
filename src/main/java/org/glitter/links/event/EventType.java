package org.glitter.links.event;

import org.glitter.links.event.handler.EventHandler;
import org.glitter.links.event.handler.SetHandler;

public enum EventType {
    SET(0,"把设备的属性置为某个值", SetHandler.class),
    ;

    Integer type;
    String remark;
    Class<? extends EventHandler> handler;

    EventType(Integer type, String remark, Class<? extends EventHandler> handler) {
        this.type = type;
        this.remark = remark;
        this.handler = handler;
    }
}
