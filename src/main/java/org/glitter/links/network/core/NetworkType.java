package org.glitter.links.network.core;

public enum NetworkType {
    TCP_CLIENT("tcp客户端"),
    TCP_SERVER("tcp服务端"),
    AMQP_SERVICE_SERVER("AMQP服务端"),
    ;
    public String name;

    NetworkType(String name) {
        this.name = name;
    }
}
