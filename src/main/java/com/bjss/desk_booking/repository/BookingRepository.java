package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.Booking;

import java.sql.Date;

public interface BookingRepository {

    public void createBooking(Booking booking);
    public void getBookingId(Booking booking);

}
