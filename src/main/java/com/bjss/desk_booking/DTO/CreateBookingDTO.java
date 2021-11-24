package com.bjss.desk_booking.DTO;

import java.sql.Date;

//This class is only used for creating records in the database
//Doesn't have a booking ID, as they are added incrementally in the DB

public class CreateBookingDTO {

    private Date date;
    private int deskId;
    //private int userId;

    public CreateBookingDTO(int deskId, Date date/*, int userId*/){
        this.date = date;
        this.deskId = deskId;
    //    this.userId = userId;
    }

    public Date getDate(){
        return date;
    }

    public int getDeskId(){
        return deskId;
    }

//    public int getUserId(){
//        return userId;
//    }

}
