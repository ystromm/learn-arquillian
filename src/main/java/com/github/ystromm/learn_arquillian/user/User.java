package com.github.ystromm.learn_arquillian.user;


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

    public static User user(long id, String name) {
        return new User(id, name);
    }
}
