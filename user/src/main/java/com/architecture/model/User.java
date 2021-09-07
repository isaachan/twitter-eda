package com.architecture.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String name;
    private List<Long> follows = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getFollows() {
        return follows;
    }

    public void addFollows(Long follow) {
        this.follows.add(follow);
    }

}
