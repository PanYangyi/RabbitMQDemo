package com.pyy.rabbitmq.util;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * @Author panyangyi
 * @create 2020/4/26 2:58
 */
public class User {

    private String userId;

    private String username;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
