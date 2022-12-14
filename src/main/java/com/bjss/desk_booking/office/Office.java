package com.bjss.desk_booking.office;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.desk.Desk;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id", updatable = false, nullable = false)
    private int officeId;
    private String officeName;

    @OneToMany(mappedBy = "office") // One to many add the office_id to the desk table
    List<Desk> deskList = new ArrayList<>();

    public Office() {
    }

    public Office(int officeId, String officeName) {
        this.officeId = officeId;
        this.officeName = officeName;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

}



