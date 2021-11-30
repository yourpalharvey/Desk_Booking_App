package com.bjss.desk_booking.desk;


import com.bjss.desk_booking.booking.Booking;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

     private int deskID;
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

    public void setDeskImageName(String deskImageName) {
        this.deskImageName = deskImageName;
    }

    public Desk(int deskID, String name, boolean hasStanding, boolean hasWindow, boolean hasDualMonitor) {
        this.deskID = deskID;
        this.deskName = name;

    }

    public Desk() {

    }

        /*save image method*/
    @Transient
    public String getImageName()
    {
        System.out.println("here");
        if (deskImageName== null) return null;


        return "/desk/" + deskID + "/" + deskImageName;
    }


    //important method to create


    public String getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(String deskStatus) {
        this.deskStatus = deskStatus;
    }


    public int getDeskID() {
        return deskID;
    }

    public void setDeskID(int deskID) {
        this.deskID = deskID;
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
        return "Desk deskID: " + deskID
                + "\nDesk name: " + deskName
                /*+ "\nStanding: " + hasStanding
                + "\nWindow: " + hasWindow
                + "\nDualMonitor: " + hasDualMonitor*/;

    }

}
