package org.glitter.links.thing;

public enum ThingType {
    NUMBER(0),
    VAR_CHART(1),
    ENUM(2),
    ;

    Integer type;

    ThingType(Integer type) {
        this.type = type;
    }
}
