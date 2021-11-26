package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.DTO.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBookingRepo extends JpaRepository<Booking,Integer> {


}
