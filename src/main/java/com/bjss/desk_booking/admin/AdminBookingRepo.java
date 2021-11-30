package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AdminBookingRepo extends JpaRepository<Booking,Integer> {

    List<Booking> findAllBystartdateBetween(
            Date bookingStartDate,
            Date bookingEndDate);




}
