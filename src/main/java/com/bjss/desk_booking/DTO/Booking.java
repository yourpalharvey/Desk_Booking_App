package com.bjss.desk_booking.DTO;

import javax.persistence.*;
import java.sql.Date;

import static javax.persistence.FetchType.LAZY;


@Entity
public class Booking {



    @Id
    private int bookingId;
    private Date date;
    private int deskId;
    private boolean booked;
    @ManyToOne  //creating Many to one relation with user
    @JoinColumn(name = "userId", nullable = false) //UserId from user class will be the foreign key in the booking table
    private  User user;

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
