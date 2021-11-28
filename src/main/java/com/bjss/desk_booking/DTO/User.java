package com.bjss.desk_booking.DTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
     int userId;
    private String username;
    private String password;
    private boolean isAdmin;
    @OneToMany(mappedBy = "user")  //Creating one to many relation with booking class and Using user object from Booking class
    List<Booking> bookingList=new ArrayList<>();

    public User(int id, String username, String password, boolean isAdmin){
        this.userId = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }
}
