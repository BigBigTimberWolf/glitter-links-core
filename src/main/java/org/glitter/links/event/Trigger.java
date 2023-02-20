package org.glitter.links.event;

import org.glitter.links.Value;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */
public interface Trigger {

    Object getValue();

    Boolean execStatus();

    void setValue(Object value);

    Boolean must();

    Boolean getStatus();

    Boolean check(Value value);
}
