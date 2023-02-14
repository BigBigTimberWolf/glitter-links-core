package org.glitter.links.network.core;

import org.glitter.links.session.DeviceSession;
import reactor.core.publisher.Mono;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/2
 */
public interface NetworkManager {

    <T extends Network>Mono<T> getNetwork(NetworkType type,String id);

    Mono<Void> shutdown(NetworkType type, String id);
}
