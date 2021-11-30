package com.bjss.desk_booking.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface BookingRepository extends JpaRepository<Booking, Integer> {


}
