package com.bjss.desk_booking.office;

import java.util.List;

public interface OfficeService {

    public List<Office> findAll();

    public Office findById(int officeId);

    public void save(Office office);

    public Office findByName(String officeName);

}
