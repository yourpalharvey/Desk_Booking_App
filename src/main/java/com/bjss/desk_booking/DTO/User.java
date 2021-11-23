package com.bjss.desk_booking.DTO;

public class User {

    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(int id, String username, String password, boolean isAdmin){
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

}
