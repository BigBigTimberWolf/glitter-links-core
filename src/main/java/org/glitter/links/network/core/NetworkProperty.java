package org.glitter.links.network.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 创建网关所需要的实体
 * @author Xiaohan.Yuan
 * @date 2023/2/2
 */
@Data
public class NetworkProperty implements Serializable {
    private String id;

    private String name;

    private boolean enabled;

    private Map<String, config> configurations;


    public static class config{

    }
}
