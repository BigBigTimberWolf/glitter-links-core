package org.glitter.links.event.handler;

import lombok.extern.slf4j.Slf4j;
import org.glitter.links.event.Event;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */
@Slf4j
public class SetHandler implements EventHandler{


    @Override
    public void exec(Event event) {
        log.info("执行：{}",event.getId());
    }
}
