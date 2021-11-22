package com.bjss.desk_booking;

import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.repository.BookingRepositoryJDBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;

@SpringBootApplication
public class DeskBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingApplication.class, args);
	}



}
