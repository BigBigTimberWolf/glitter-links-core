package org.glitter.links.network.core;

import reactor.core.publisher.Mono;

/**
 * 用来构建不同类型的网关
 * @author Xiaohan.Yuan
 * @date 2023/2/2
 */
public interface NetworkProvider {


    /**
     * @return 支持的类型
     * @see org.glitter.links.network.core.NetworkType
     * */
    NetworkType getType();

    /**
     * 根据配置文件创建网关，创建完成后连接需要处于活跃状态
     * @param config  创建网关所需要的配置信息
     * @return 已经创建连接的网关
     * */
    Network createNetwork(NetworkProperty.config config);

    /**
     * @param config 新的配置文件
     * @param network 会对当前network进行重载配置
     * */
    void reload(Network network,NetworkProperty.config config);


    /**
     * @param properties 网关的统一数据格式
     * @return  网关类型的特定实现类，使用类可以创建对应网关
     * */
    Mono<NetworkProperty.config> createConfig(NetworkProperty properties);




}
