package org.glitter.links.network.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/2
 */
@Component
public class DefaultNetworkManager implements NetworkManager, BeanPostProcessor {

    private final NetworkConfigManager networkConfigManager;

    private final Map<String, Map<String, Network>> store = new ConcurrentHashMap<>();

    private final Map<String, NetworkProvider> providerSupport = new ConcurrentHashMap<>();

    public DefaultNetworkManager(NetworkConfigManager networkConfigManager) {
        this.networkConfigManager = networkConfigManager;
    }


    /**
     * 根据网关的类型和id获取网关，如果网关不存在就根据配置创建网关
     * @param id 网关的id
     * @param type 网关的类型
     * */
    @Override
    public <T extends Network> Mono<T> getNetwork(NetworkType type, String id) {
        Map<String, Network> networkMapGroupByType = getNetworksByType(type.name);
        return Mono.justOrEmpty(networkMapGroupByType.get(id)).filter(Network::isLive)
                .switchIfEmpty(Mono.defer(()->createNetwork(type,id)))
                .map(n->(T) n);
    }

    private Map<String, Network> getNetworksByType(String typeName){
        return store.computeIfAbsent(typeName, _name -> new ConcurrentHashMap<>());
    }

    @Override
    public Mono<Void> shutdown(NetworkType type, String id) {
        return null;
    }


    /**
     * 根据类型和id创建网关
     * @param id 网关id
     * @param type 网关类型
     * */
    public Mono<Network> createNetwork(NetworkType type, String id) {
       return Mono.justOrEmpty(providerSupport.get(type.name))
                        .switchIfEmpty(Mono.error(()->new UnsupportedOperationException("不支持该类型的网关")))
                .flatMap(networkProvider -> networkConfigManager.getConfig(type, id)
                        .switchIfEmpty(Mono.error(()->new UnsupportedOperationException("网络[" + type.name + "]配置[" + id + "]不存在")))
                        .filter(NetworkProperty::isEnabled)
                        .switchIfEmpty(Mono.error(() -> new UnsupportedOperationException("网络[" + type.name + "]配置[" + id + "]已禁用")))
                        .flatMap(networkProvider::createConfig)
                        .map(config -> createNetwork(networkProvider,id,config))

                )
        ;
    }

    public Network createNetwork(NetworkProvider networkProvider,
                                 String id,
                                 NetworkProperty.config config
                                 ){
        return getNetworksByType(networkProvider.getType().name).compute(id,(s,network)->{
            if (network==null){
                network = networkProvider.createNetwork(config);
            }else {
                networkProvider.reload(network,config);
            }
            return network;
        });

    }

    public void register(NetworkProvider provider) {
        this.providerSupport.put(provider.getType().name, provider);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof NetworkProvider) {
            register(((NetworkProvider) bean));
        }
        return bean;
    }
}
