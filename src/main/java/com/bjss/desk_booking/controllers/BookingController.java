package com.bjss.desk_booking.controllers;


import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
public class BookingController {

    private BookingRepository bookingRepo;

    @Autowired
    public BookingController(BookingRepository bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    @RequestMapping(path = "/user/createBooking", method = RequestMethod.POST)
    public String createBooking(Booking booking){
        System.out.println("BOOKED!!!!!");
        bookingRepo.createBooking(booking);
        return "redirect:/user/booking";
    }



}
