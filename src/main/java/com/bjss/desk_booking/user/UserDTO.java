package com.bjss.desk_booking.user;

public class UserDTO {

    private String username;

    public UserDTO(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
