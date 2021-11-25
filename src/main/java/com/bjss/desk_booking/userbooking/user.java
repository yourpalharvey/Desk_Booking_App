package com.bjss.desk_booking.userbooking;

public class user {
    String checkInTime ;			//入住时间
    String leaveTime ;				//离开时间
    String deskType ;				//房间型号
    String name ;					//名字
    int covers ;					//人数
    int deskNum ;					//房间号
    user(String checkInTime, String leaveTime, String deskType, String name, int covers, int deskNum) {
        this.covers = covers ;
        this.deskNum = deskNum ;
        this.checkInTime = checkInTime ;
        this.leaveTime = leaveTime ;
        this.name = name  ;
        this.deskType = deskType ;
    }
    int getCovers() {
        return covers ;
    }
    int getRoomNum() {
        return deskNum ;
    }
    String getName() {
        return name ;
    }
    String getCheckInTime() {
        return checkInTime ;
    }
    String getLeaveTime() {
        return leaveTime ;
    }
    String getRoomType() {
        return deskType ;
    }
    void setCheckInTime(int covers) {
        this.covers = covers ;
    }
    void setRoomNum(int deskNum) {
        this.deskNum = deskNum ;
    }
    void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime ;
    }
    void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime ;
    }
    void setname(String name) {
        this.name = name  ;
    }
    void setRoomType(String roomType) {
        this.deskType = deskType ;
    }
}
