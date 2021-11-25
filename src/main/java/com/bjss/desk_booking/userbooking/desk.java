package com.bjss.desk_booking.userbooking;

public class desk {
    String type ;		//房间型号
    String state ;		//当前房间状态
    int deskNum ;		//房号
    desk() {}
    desk(String type, int roomNum, String state) {
        this.type = type ;
        this.deskNum = roomNum ;
        this.state = state ;
    }
    void setState(String state) {	//修改房间状态
        this.state = state ;
    }
    int getRoomNum() {				//返回房号
        return deskNum ;
    }
    String getType() {				//返回房间类型
        return type ;
    }
    String getState() {				//返回房间状态
        return state ;
    }
}
