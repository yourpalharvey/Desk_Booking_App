package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.desk.Desk;

import java.util.List;

public interface AdminBookingService {

    public List<Booking> findAll();

    public Booking findById(int bookingId);


    public void save(Booking booking);

    public void deleteById(int bookingId);
}
