package com.bjss.desk_booking.DTO;


import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class Desk {

    @Id
    private int deskID;
    private String name;
    private boolean hasStanding;
    private boolean hasWindow;
    private boolean hasDualMonitor;
    String deskImageName;

    public String getDeskImageName() {
        return deskImageName;
    }

    public void setDeskImageName(String deskImageName) {
        this.deskImageName = deskImageName;
    }

    public Desk(int deskID, String name, boolean hasStanding, boolean hasWindow, boolean hasDualMonitor) {
        this.deskID = Desk.this.deskID;
        this.name = name;
        this.hasStanding = hasStanding;
        this.hasWindow = hasWindow;
        this.hasDualMonitor = hasDualMonitor;
    }

    public Desk() {

    }

        /*save image method*/
    @Transient
    public String getImageName()
    {

        if (deskImageName== null) return null;

        /*return "/home/pi/devfurkan/Projects-folder/" + projectID + "/" + imageName;*/
        return "/Projects-folder/" + deskID + "/" + deskImageName;
    }

    //important method to create
    public int getdeskID(){
        return deskID;
    }

    public int getDeskID() {
        return deskID;
    }

    public void setDeskID(int deskID) {
        this.deskID = deskID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasStanding() {
        return hasStanding;
    }

    public void setHasStanding(boolean hasStanding) {
        this.hasStanding = hasStanding;
    }

    public boolean isHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(boolean hasWindow) {
        this.hasWindow = hasWindow;
    }

    public boolean isHasDualMonitor() {
        return hasDualMonitor;
    }

    public void setHasDualMonitor(boolean hasDualMonitor) {
        this.hasDualMonitor = hasDualMonitor;
    }

    public String getName(){
        return name;
    }

    public boolean hasStanding(){
        return hasStanding;
    }

    public boolean hasWindow(){
        return hasWindow;
    }

    public boolean hasDualMonitor(){
        return hasDualMonitor;
    }

    @Override
    public String toString(){
        return "Desk deskID: " + deskID
                + "\nDesk name: " + name
                + "\nStanding: " + hasStanding
                + "\nWindow: " + hasWindow
                + "\nDualMonitor: " + hasDualMonitor;

    }

}
