package com.zblouse.library;

import java.util.HashMap;
import java.util.Map;

public class User {

    public static final String DATABASE_USER_ID_KEY = "uid";
    public static final String DATABASE_NAME_KEY = "name";
    private String userId;
    private String name;

    public User(String userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getName(){
        return this.name;
    }

    public Map<String, Object> getMap(){
        Map<String, Object> user = new HashMap<>();
        user.put(DATABASE_NAME_KEY,name);
        user.put(DATABASE_USER_ID_KEY, userId);
        return user;
    }
}
