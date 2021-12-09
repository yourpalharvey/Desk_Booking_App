package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.desk.Desk;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", updatable = false, nullable = false)
    private int bookingId;
    private Date startDate;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

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

    public int getUserId(){
        return user.getUserId();
    }

    public String getOfficeName(){
        return desk.getOffice().getOfficeName();
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
        return desk.getDeskId();
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
        return "ID: " + bookingId + "\nDate: " + startDate + "\nDesk ID: " + desk.getDeskId();
    }

    public Date getDate() {
        return startDate;
    }
}
