package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.office.Office;
import com.bjss.desk_booking.user.User;

import java.sql.Date;

//this class is used for displaying Bookings as JSON. Easier to use this than Booking class
// to create a JSON string because Booking contained other objects causing a stack overflow
// also, the below details are all that are needed by the front end

public class BookingDTO {

    private int bookingId;

    private String date;

    private int deskId;

    private String deskImageName;

    private boolean booked;

    private String officeLocation;

    //this is for returning to quick booking page
    public BookingDTO(String date, int deskId, String officeLocation){
        this.date = date;
        this.deskId = deskId;
        this.officeLocation = officeLocation;
    }

    //this is for returning desk bookings to the myBookings page
    public BookingDTO(String officeLocation, int bookingId, String date, int deskId){
        this.bookingId = bookingId;
        this.date = date;
        this.deskId = deskId;
        this.officeLocation = officeLocation;
    }

    //this is for returning the individual desk bookings in BookingPage
    public BookingDTO(String date, int deskId, boolean booked, String deskImageName){
        this.date = date;
        this.deskId = deskId;
        this.booked = booked;
        this.deskImageName = deskImageName;
    }

    public BookingDTO(int bookingId, String date, int deskId, String deskImageName){
        this.bookingId = bookingId;
        this.date = date;
        this.deskId = deskId;
        this.deskImageName = deskImageName;
    }



    public String getDeskImageName() {
        return deskImageName;
    }

    public void setDeskImageName(String deskImageName) {
        this.deskImageName = deskImageName;
    }

    public boolean getBooked(){
        return booked;
    }

    public void setBooked(boolean booked){
        this.booked = booked;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }
}
