package com.bjss.desk_booking.desk;


import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.office.Office;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int deskId;
    String deskName;
    String desktype;
    String deskPosition;
    int monitorOption;
    String deskStatus;
    String deskImageName;

    public String getDeskImageName() {
        return deskImageName;
    }


    @OneToMany(mappedBy = "desk")  //Creating one to many relation with booking class and Using user object from Booking class
    List<Booking> bookingList=new ArrayList<>();

    @ManyToOne  //creating Many to one relation with office
    @JoinColumn(name = "office_id", nullable = false) //officeId from user class will be the foreign key in the booking table
    private Office office;

    public void setDeskImageName(String deskImageName) {
        this.deskImageName = deskImageName;
    }

    public Desk(String name, Office office, String desktype, String deskPosition, int monitorOption) {
        this.deskName = name;
        this.office = office;
        this.desktype = desktype;
        this.deskPosition = deskPosition;
        this.monitorOption = monitorOption;

    }

    public Desk() {

    }

    /*save image method*/
    @Transient
    public String getPhotosImagePath() {
        if (deskName == null || deskId == -1) return null;

        return "desk/" + deskId + "/" + deskImageName;
    }

    //important method to create


    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(String deskStatus) {
        this.deskStatus = deskStatus;
    }


    public int getDeskID() {
        return deskId;
    }

    public void setDeskID(int deskID) {
        this.deskId = deskID;
    }

    public void setName(String name) {
        this.deskName = name;
    }
    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public String getDesktype() {
        return desktype;
    }

    public void setDesktype(String desktype) {
        this.desktype = desktype;
    }

    public String getDeskPosition() {
        return deskPosition;
    }

    public void setDeskPosition(String deskPosition) {
        this.deskPosition = deskPosition;
    }

    public int getMonitorOption() {
        return monitorOption;
    }

    public void setMonitorOption(int monitorOption) {
        this.monitorOption = monitorOption;
    }

    public String getName(){
        return deskName;
    }



    @Override
    public String toString(){
        return "Desk deskID: " + deskId
                + "\nDesk name: " + deskName
                + "\nOffice: " + office.getOfficeName();
                /*+ "\nStanding: " + hasStanding
                + "\nWindow: " + hasWindow
                + "\nDualMonitor: " + hasDualMonitor*/

    }

}
