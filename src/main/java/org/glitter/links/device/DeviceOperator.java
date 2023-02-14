package org.glitter.links.device;

import org.glitter.links.Value;
import reactor.core.publisher.Mono;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/9
 */
public interface DeviceOperator {

    /**
     * @return 获取设备在系统中的唯一标识
     * */
    String getDeviceId();

    /**
     * 可以通过getSession获取到session
     * @see org.glitter.links.session.SessionManager#getSession(String)
     * @return 获取当前设备的会话id
     * */
    Mono<String> getSessionId();

    /**
     * @return 设备的地址，通常是ip地址
     * */
    Mono<String> getAddress();


    /**
     * @return 设备上线时间
     * */
    Mono<Long> getOnlineTime();

    /**
     * @return 设备离线时间
     * */
    Mono<Long> getOfflineTime();

    Mono<Boolean> online();

    Mono<Boolean> offline();

    Mono<Value> getProperty(String propertyName);

    Mono<Value> updateProperty(String propertyName,Object value);
}
