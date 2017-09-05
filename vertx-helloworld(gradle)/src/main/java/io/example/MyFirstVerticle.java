package io.example;

import io.vertx.core.AbstractVerticle;

/**
 * Created by MelloChan on 2017/9/5.
 * 配置类
 */
public class MyFirstVerticle  extends AbstractVerticle{
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req->{
            req.response()
                    .putHeader("content-type","text/plain")
                    .end("Hello World!");
        }).listen(8080);
    }
}
