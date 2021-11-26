package com.bjss.desk_booking.DTO;

import javax.persistence.*;
import java.sql.Date;

import static javax.persistence.FetchType.LAZY;


@Entity
public class Booking {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", updatable = false, nullable = false)
    private int bookingId;
    private Date date;
    private int deskId;
    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "userId", nullable = false) //UserId from user class will be the foreign key in the booking table
    private  User user;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking(int id, int deskId, Date date){
        this.bookingId = id;
        this.date = date;
        this.deskId = deskId;


    }

    public Booking() {

    }

    public Date getDate(){
        return date;
    }

    public int getDeskId(){
        return deskId;
    }

    public int getId(){
        return bookingId;
    }

    public String toString(){
        return "ID: " + bookingId + "\nDate: " + date + "\nDesk ID: " + deskId;
    }

}
