package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.CreateBookingDTO;

import java.sql.Date;

public interface BookingRepository {

    public void createBooking(CreateBookingDTO createBooking);
    public Object getBookingByDeskAndDate(int deskId, Date date);
    public boolean bookingExists(int deskId, Date date);

}
