package org.glitter.links.event.handler;

import lombok.extern.slf4j.Slf4j;
import org.glitter.links.event.Event;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */
public interface EventHandler {
    void exec(Event event);
}
