package com.bjss.desk_booking.booking;

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

    private int monitorOption;

    private String deskPosition;

    private String deskType;

    private String userBooked;
        //used to disable the button if user already has booking on specific day
    private boolean disableButton;
        //used to display a cancel button for a user's booking in mybookingpage
    private boolean cancelButton;

    private boolean checkedIn;


    //this is for returning to quick booking page
    public BookingDTO(String date, int deskId, String officeLocation){
        this.date = date;
        this.deskId = deskId;
        this.officeLocation = officeLocation;
    }

    //this is for returning information about cancelled bookings when a desk is deleted
    public BookingDTO(String date, String userBooked, int deskId){
        this.date = date;
        this.userBooked = userBooked;
        this.deskId = deskId;
    }


    //this is for returning to quick booking page
    public BookingDTO(String date, int deskId, String officeLocation, boolean booked){
        this.date = date;
        this.deskId = deskId;
        this.officeLocation = officeLocation;
        this.booked = booked;
    }

    //this is for returning the individual desk bookings in BookingPage
    public BookingDTO(int bookingId, String date, int deskId, boolean booked, String deskImageName,
                      String officeLocation, int monitorOption, String deskPosition,
                      String deskType, String userBooked, boolean disableButton,
                      boolean cancelButton){
        this.bookingId = bookingId;
        this.date = date;
        this.deskId = deskId;
        this.booked = booked;
        this.deskImageName = deskImageName;
        this.officeLocation = officeLocation;
        this.monitorOption = monitorOption;
        this.deskPosition = deskPosition;
        this.deskType = deskType;
        this.userBooked = userBooked;
        this.disableButton = disableButton;
        this.cancelButton = cancelButton;
    }

    //this is for returning to myBookingPage
    public BookingDTO(int bookingId, String date, int deskId, String deskImageName, String officeLocation, boolean checkedIn){
        this.bookingId = bookingId;
        this.date = date;
        this.deskId = deskId;
        this.deskImageName = deskImageName;
        this.officeLocation = officeLocation;
        this.checkedIn = checkedIn;
    }

    public boolean isDisableButton() {
        return disableButton;
    }

    public void setDisableButton(boolean disableButton) {
        this.disableButton = disableButton;
    }

    public boolean isBooked() {
        return booked;
    }

    public int getMonitorOption() {
        return monitorOption;
    }

    public void setMonitorOption(int monitorOption) {
        this.monitorOption = monitorOption;
    }

    public String getDeskPosition() {
        return deskPosition;
    }

    public void setDeskPosition(String deskPosition) {
        this.deskPosition = deskPosition;
    }

    public String getDeskType() {
        return deskType;
    }

    public void setDeskType(String deskType) {
        this.deskType = deskType;
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

    public String getUserBooked() {
        return userBooked;
    }

    public void setUserBooked(String userBooked) {
        this.userBooked = userBooked;
    }

    public boolean isCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(boolean cancelButton) {
        this.cancelButton = cancelButton;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
