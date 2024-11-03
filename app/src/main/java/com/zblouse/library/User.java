package com.zblouse.library;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO for the User object
 */
public class User implements Serializable {

    public static final String DATABASE_USER_ID_KEY = "uid";
    public static final String DATABASE_NAME_KEY = "name";
    private String userId;
    private String name;

    public User(String userId, String name){
        this.userId = userId;
        this.name = name;
    }

    public User(Map<String, Object> data){
        this.userId = (String)data.get(DATABASE_USER_ID_KEY);
        this.name = (String)data.get(DATABASE_NAME_KEY);;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getName(){
        return this.name;
    }

    /**
     * Returns a map to be stored in Firestore
     * @return
     */
    public Map<String, Object> getMap(){
        Map<String, Object> user = new HashMap<>();
        user.put(DATABASE_NAME_KEY,name);
        user.put(DATABASE_USER_ID_KEY, userId);
        return user;
    }
}
