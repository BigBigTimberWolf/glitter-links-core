package org.glitter.links.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/1
 */
public abstract class VertxVerticle extends AbstractVerticle{
    String name;

    protected DeploymentOptions options= new DeploymentOptions();

    public DeploymentOptions getOptions(){
        return this.options;
    };
}
