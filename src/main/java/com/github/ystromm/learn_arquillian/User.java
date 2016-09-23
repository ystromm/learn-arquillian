package com.github.ystromm.learn_arquillian;

/**
 * Created by mac on 2016-09-22.
 */
public class User {
    private final long id;
    private final String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
