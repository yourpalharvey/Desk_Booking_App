package com.bjss.desk_booking.admin;

public class PendingBookingDTO {
    private int bookingId;

    private String date;

    private int deskId;

    private String deskImageName;

    private String officeLocation;

    private String userBooked;

    public PendingBookingDTO(int bookingId, String date, int deskId, String deskImageName, String officeLocation, String userBooked) {
        this.bookingId = bookingId;
        this.date = date;
        this.deskId = deskId;
        this.deskImageName = deskImageName;
        this.officeLocation = officeLocation;
        this.userBooked = userBooked;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getDeskImageName() {
        return deskImageName;
    }

    public void setDeskImageName(String deskImageName) {
        this.deskImageName = deskImageName;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getUserBooked() {
        return userBooked;
    }

    public void setUserBooked(String userBooked) {
        this.userBooked = userBooked;
    }
}
