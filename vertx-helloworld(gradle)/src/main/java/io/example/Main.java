package io.example;

import io.vertx.core.Vertx;

/**
 * Created by MelloChan on 2017/9/5.
 * 主类
 */
public class Main {
    public static void main(String[] args) {
        Vertx vertx=Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
