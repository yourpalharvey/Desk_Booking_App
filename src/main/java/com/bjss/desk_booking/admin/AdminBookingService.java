package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;

import java.util.List;

public interface AdminBookingService {

    public List<Booking> findAll();

    public Booking findById(int bookingId);

    public void save(Booking booking);

    public void deleteById(int bookingId);
}
