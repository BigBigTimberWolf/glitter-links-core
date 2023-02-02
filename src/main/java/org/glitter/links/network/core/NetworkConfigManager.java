package org.glitter.links.network.core;

import reactor.core.publisher.Mono;

/**
 * 必须实现该管理类用以获取网关信息
 * @author Xiaohan.Yuan
 * @date 2023/2/2
 */
public interface NetworkConfigManager {
    Mono<NetworkProperty> getConfig(NetworkType networkType, String id);
}
