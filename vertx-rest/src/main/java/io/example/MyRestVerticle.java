package io.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by MelloChan on 2017/9/6.
 * 业务类
 */
public class MyRestVerticle extends AbstractVerticle {
    private Map<Integer, Person> maps = new LinkedHashMap<>();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        creatData();
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            routingContext.response()
                    .putHeader("content-type", "text/html")
                    .end("<h1>My First Vertx Rest Application</h1>");
        });
        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.get("/api/person").handler(this::getAll);
        router.route("/api/person").handler(BodyHandler.create());
        router.get("/api/person/:id").handler(this::getOne);
        router.post("/api/person").handler(this::addOne);
        router.put("/api/person/:id").handler(this::updateOne);
        router.delete("/api/person/:id").handler(this::deleteOne);
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });

    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=UTF-8")
                .end(Json.encodePrettily(maps.values()));
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        System.out.println("id:" + id);
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.parseInt(id);
            final Person person = maps.get(idAsInteger);
            if (person == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                routingContext.response().putHeader("content-type", "application/json;charset=UTF-8")
                        .end(Json.encodePrettily(maps.values()));
            }
        }
    }

    private void addOne(RoutingContext routingContext) {
        final Person person = Json.decodeValue(routingContext.getBodyAsString(), Person.class);
        maps.put(person.getId(), person);
        routingContext.response().setStatusCode(201)
                .putHeader("content-type", "application/json;charset=UTF-8")
                .end(Json.encodePrettily(maps.values()));
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        System.out.println("id:" + id + " data:" + json);
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.parseInt(id);
            Person person = maps.get(idAsInteger);
            if (person == null) {
                routingContext.response().setStatusCode(401).end();
            } else {
                person.setName(json.getString("name"));
                person.setAddress(json.getString("address"));
                routingContext.response().putHeader("content-type", "application/json;charset=UTF-8")
                        .end(Json.encodePrettily(maps.values()));
            }
        }
    }

    private void deleteOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        System.out.println("id:" + id);
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.parseInt(id);
            maps.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(200).end();
    }

    public void creatData() {
        Person person1 = new Person("mello", "ZhuHai");
        Person person2 = new Person("yuki", "未知");
        maps.put(person1.getId(), person1);
        maps.put(person2.getId(), person2);
    }
}
