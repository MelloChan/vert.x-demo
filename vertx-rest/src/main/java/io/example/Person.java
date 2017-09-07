package io.example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MelloChan on 2017/9/7.
 */
public class Person {
    private static final AtomicInteger COUNT = new AtomicInteger();
    private Integer id;
    private String name;
    private String address;

    public Person(String name, String address) {
        this.id = COUNT.getAndIncrement();
        this.name = name;
        this.address = address;
    }

    public Person() {
        this.id = COUNT.getAndIncrement();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
