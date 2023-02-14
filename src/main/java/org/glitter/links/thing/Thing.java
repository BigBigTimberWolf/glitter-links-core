package org.glitter.links.thing;

import org.glitter.links.Value;
import reactor.core.publisher.Mono;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/9
 */
public interface Thing {
    /**
     * @return 模型唯一标识
     * */
    String getId();

    ThingType getType();

    Value getValue();

    Value updateValue();
}
