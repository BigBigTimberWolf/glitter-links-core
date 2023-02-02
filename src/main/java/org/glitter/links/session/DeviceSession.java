package org.glitter.links.session;

import org.glitter.links.message.EncodedMessage;
import reactor.core.publisher.Mono;

/**
 * 设备会话,通常对应一个设备连接
 *
 * @author xiaohan.yuan
 * @since 1.0.0
 */
public interface DeviceSession {

    /**
     * return 会话ID
     * */
    String getId();

    /**
     * 设备ID
     * */
    String getDeviceId();

    // todo 设备操作对象

    /**
     * 上次心跳时间
     * */
    long lastPingTime();

    /**
     * Session 创建时间
     * */
    long createTime();

    /**
     * 发送消息给会话
     * */
    Mono<Boolean> send(EncodedMessage encodedMessage);
}
