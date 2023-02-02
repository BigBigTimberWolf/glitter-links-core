package org.glitter.links.config;

import io.vertx.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/1
 */
@Configuration
public class VertxConfiguration {
    public static Vertx vertx = Vertx.vertx();

    final List<VertxVerticle> verticleList;


    public VertxConfiguration(List<VertxVerticle> verticleList) {
        this.verticleList = verticleList;
        initVerticle();
    }

    private void initVerticle(){
        List<Future> futures = new ArrayList<>();
        for (AbstractVerticle abstractVerticle : verticleList) {
            futures.add(Future.<String>future(data -> vertx.deployVerticle(abstractVerticle, new DeploymentOptions().setInstances(1), data)));
        }

        CompositeFuture.all(futures);
    }
}
