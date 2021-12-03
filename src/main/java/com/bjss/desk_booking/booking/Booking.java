package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.desk.Desk;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
//import java.util.Date;
import java.sql.Date;


@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", updatable = false, nullable = false)
    private int bookingId;

    //@Temporal(TemporalType.DATE)
    private Date startDate;

    //@Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "userId", nullable = false) //UserId from user class will be the foreign key in the booking table
    private User user;

    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "deskId", nullable = false) //UserId from user class will be the foreign key in the booking table
    private Desk desk;

    //this constructor is for the quick booking system - todo: I think we should remove the endDate and just insert one booking per day
    public Booking(Date startDate, User user, Desk desk){
        this.startDate = startDate;
        this.user = user;
        this.desk = desk;
    }

    public Desk getDesk(){
        return desk;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking(int id, int deskId, Date date){
        this.bookingId = id;
    }

    public Booking() {
    }

    public int getDeskId() {
        return desk.getDeskID();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getId(){
        return bookingId;
    }

    public Date getStartdate() {
        return startDate;
    }

    public void setStartdate(Date startdate) {
        this.startDate = startdate;
    }

    public String toString(){
        return "ID: " + bookingId + "\nDate: " + startDate + "\nDesk ID: " + desk.getDeskID();
    }

    public Date getDate() {
        return startDate;
    }
}
