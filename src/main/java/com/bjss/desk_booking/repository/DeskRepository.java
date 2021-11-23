package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.CreateDeskDTO;

//@Service
public interface DeskRepository {

    public Object getDeskByName(String name);
    public void addDesk(CreateDeskDTO createDesk);
}
