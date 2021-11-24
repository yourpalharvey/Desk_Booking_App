package com.bjss.desk_booking.DTO;

public class CreateDeskDTO {

    private String name;
    private boolean hasStanding;
    private boolean hasWindow;
    private boolean hasDualMonitor;

    public CreateDeskDTO(String name, Boolean hasStanding, Boolean hasWindow, Boolean hasDualMonitor) {
        this.name = name;
        this.hasStanding = falseIfNull(hasStanding);
        this.hasWindow = falseIfNull(hasWindow);
        this.hasDualMonitor = falseIfNull(hasDualMonitor);
    }

    //Added this method because the checkbox in HTML form returns null if not checked.
    //As a boolean cannot be null, I've used a Boolean object in constructor arguments then called this method
    //todo - look for a way to improve this from the HTML directly
    public boolean falseIfNull(Boolean b){
        return b != null;
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
        return "Desk name: " + name
                + "\nStanding: " + hasStanding
                + "\nWindow: " + hasWindow
                + "\nDualMonitor: " + hasDualMonitor;

    }

}