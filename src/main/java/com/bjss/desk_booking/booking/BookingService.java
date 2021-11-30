package com.bjss.desk_booking.booking;

import java.util.List;

public interface BookingService {

    public List<Booking> findAll();

    public Booking findById(int bookingId);

    public void save(Booking booking);

    public void deleteById(int bookingId);
}
