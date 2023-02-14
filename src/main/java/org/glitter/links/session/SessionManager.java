package org.glitter.links.session;

import reactor.core.publisher.Mono;

/**
 * session管理
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
public interface SessionManager {
    /**
     * 根据设备ID或者会话ID获取设备会话
     *
     * @param idOrDeviceId 设备ID或者会话ID
     * @return 设备会话, 不存在则返回<code>null</code>
     */
    DeviceSession getSession(String idOrDeviceId);

    /**
     * 注册新到设备会话,如果已经存在相同设备ID到会话,将注销旧的会话.
     *
     * @param session 新的设备会话
     * @return 旧的设备会话, 不存在则返回<code>null</code>
     */
    DeviceSession register(DeviceSession session);

    /**
     * 使用会话ID或者设备ID注销设备会话
     *
     * @param idOrDeviceId 设备ID或者会话ID
     * @return 被注销的会话, 不存在则返回<code>null</code>
     */
    DeviceSession unregister(String idOrDeviceId);

    boolean sessionIsAlive(String deviceId);
}
