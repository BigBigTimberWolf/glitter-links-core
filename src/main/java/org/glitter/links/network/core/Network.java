package org.glitter.links.network.core;

public interface Network {

    String getId();

    NetworkType getType();

    /**
     * 关闭网络
     * */
    void showdown();

    /**
     * 是否存活
     * */
    boolean isLive();


}
