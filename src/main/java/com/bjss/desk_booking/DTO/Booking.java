package com.bjss.desk_booking.DTO;

import java.sql.Date;


public class Booking {

    private Date date;
    private int deskId;

    public Booking(Date date, int deskId){
        System.out.println("BOOOOOOKING");
        this.date = date;
        this.deskId = deskId;
    }

    public Date getDate(){
        return date;
    }

    public int getDeskId(){
        return deskId;
    }

}
