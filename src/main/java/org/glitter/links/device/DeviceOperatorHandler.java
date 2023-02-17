package org.glitter.links.device;

public interface DeviceOperatorHandler {
    public void updateValue(String deviceId,String name,Object value);

    public Object getValue(String name);
}
