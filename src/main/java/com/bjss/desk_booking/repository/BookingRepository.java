package com.bjss.desk_booking.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
public interface BookingRepository {

    public Object getDeskById(String name);
}
