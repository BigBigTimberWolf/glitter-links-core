package org.glitter.links.session;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
@Component
public class DefaultSessionManager implements SessionManager{
    private final Map<String, DeviceSession> repository = new ConcurrentHashMap<>(4096);


    @Override
    public Flux<DeviceSession> getSession(String idOrDeviceId) {
        DeviceSession session = repository.get(idOrDeviceId);
        if (session == null || !session.isAlive()) {
            return Flux.empty();
        }
        return Flux.just(session);
    }

    @Override
    public Flux<DeviceSession> register(DeviceSession session) {
        DeviceSession old = repository.put(session.getDeviceId(), session);
        if (old != null) {
            //清空sessionId不同
            if (!old.getId().equals(old.getDeviceId())) {
                repository.remove(old.getId());
            }
        }
        if (!session.getId().equals(session.getDeviceId())) {
            repository.put(session.getId(), session);
        }
        return Flux.just(session);
    }

    @Override
    public Flux<DeviceSession> unregister(String idOrDeviceId) {
        return null;
    }

    @Override
    public boolean sessionIsAlive(String deviceId) {
        return false;
    }
}
