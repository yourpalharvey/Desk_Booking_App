package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.desk.Desk;

import javax.persistence.*;
//import java.util.Date;
import java.sql.Date;


@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", updatable = false, nullable = false)
    private int bookingId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "userId", nullable = false) //UserId from user class will be the foreign key in the booking table
    private User user;

    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "deskID", nullable = false) //UserId from user class will be the foreign key in the booking table
    private Desk desk;

    //this constructor is for the quick booking system - todo: I think we should remove the endDate and just insert one booking per day
    public Booking(Date startdate, User user, Desk desk){
        this.startdate = startdate;
        this.user = user;
        this.desk = desk;
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
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String toString(){
        return "ID: " + bookingId + "\nDate: "  + "\nDesk ID: ";
    }

    public Date getDate() {
        return startdate;
    }
}
