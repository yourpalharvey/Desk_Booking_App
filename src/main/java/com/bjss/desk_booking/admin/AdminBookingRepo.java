package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.DTO.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AdminBookingRepo extends JpaRepository<Booking,Integer> {

    List<Booking> findAllBystartdateBetween(
            Date bookingStartDate,
            Date bookingEndDate);




}
