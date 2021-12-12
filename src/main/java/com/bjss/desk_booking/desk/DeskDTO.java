package com.bjss.desk_booking.desk;

public class DeskDTO {

    private int deskId;
    private String deskName;
    private String deskType;
    private String deskPosition;
    private int monitorOption;
    private String deskStatus;
    private String deskImageName;
    private String officeLocation;

    public DeskDTO(int deskId, String deskName, String deskType, String deskPosition,
                   int monitorOption, String deskStatus, String deskImageName, String officeLocation) {
        this.deskId = deskId;
        this.deskName = deskName;
        this.deskType = deskType;
        this.deskPosition = deskPosition;
        this.monitorOption = monitorOption;
        this.deskStatus = deskStatus;
        this.deskImageName = deskImageName;
        this.officeLocation = officeLocation;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public String getDeskType() {
        return deskType;
    }

    public void setDeskType(String deskType) {
        this.deskType = deskType;
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

    public String getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(String deskStatus) {
        this.deskStatus = deskStatus;
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
}
