package com.bjss.desk_booking.DTO;

import java.sql.Date;


public class Booking {

    private int id;
    private Date date;
    private int deskId;

    public Booking(int id, Date date, int deskId){
        this.id = id;
        this.date = date;
        this.deskId = deskId;
    }

    public int getBookingId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public int getDeskId(){
        return deskId;
    }

}
