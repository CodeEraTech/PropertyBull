package com.doomshell.property_bull.model;

/**
 * Created by Anuj on 4/21/2017.
 */

public class User {
    public User(long id) {
        this.id = id;

    }
    private long id;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
