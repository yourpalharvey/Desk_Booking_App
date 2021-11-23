package com.bjss.desk_booking.DTO;

import java.sql.Date;


public class Booking {

    private int id;
    private Date date;
    private int deskId;

    public Booking(int id, int deskId, Date date){
        this.id = id;
        this.date = date;
        this.deskId = deskId;
    }

    public Date getDate(){
        return date;
    }

    public int getDeskId(){
        return deskId;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        return "ID: " + id + "\nDate: " + date + "\nDesk ID: " + deskId;
    }

}
