package com.bjss.desk_booking.DTO;

public class Desk {


    private int id;
    private String name;
    private boolean hasStanding;
    private boolean hasWindow;
    private boolean hasDualMonitor;


    public Desk(int id, String name, boolean hasStanding, boolean hasWindow, boolean hasDualMonitor) {
        this.id = id;
        this.name = name;
        this.hasStanding = hasStanding;
        this.hasWindow = hasWindow;
        this.hasDualMonitor = hasDualMonitor;
    }

    //important method to create
    public int getDeskId(){
        return id;
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
        return "Desk ID: " + id
                + "\nDesk name: " + name
                + "\nStanding: " + hasStanding
                + "\nWindow: " + hasWindow
                + "\nDualMonitor: " + hasDualMonitor;

    }

}
