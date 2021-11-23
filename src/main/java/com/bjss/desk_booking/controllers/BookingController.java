package com.bjss.desk_booking.controllers;

import com.bjss.desk_booking.DTO.CreateBookingDTO;
import com.bjss.desk_booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.Map;


@Controller
public class BookingController {

    private BookingRepository bookingRepo;

    @Autowired
    public BookingController(BookingRepository bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    //check if a booking already exists with the same date and desk id
    //if not, create booking record in database by calling createBooking()
    @RequestMapping(path = "/user/createBooking", method = RequestMethod.POST)
    public String createBooking(CreateBookingDTO createBooking){
        try{
            if(bookingRepo.bookingExists(createBooking.getDeskId(), createBooking.getDate())){
                System.out.println("BOOKING ALREADY EXISTS - 0 ROWS INSERTED");
                return "redirect:/";
            };
        } catch (Exception e) {
            //if record is not in the database an error will be thrown
            //if error is thrown, we can assume there is no matching data in DB so create new booking record
            bookingRepo.createBooking(createBooking);
        }

        return "redirect:/";
    }


    @RequestMapping(path = "/user/getBookingByDeskAndDate", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value="date", defaultValue = "null") Date date,
                               @RequestParam(value="deskId", defaultValue = "1") int deskId){

        ModelAndView mav = new ModelAndView();
        mav.addObject(bookingRepo.getBookingByDeskAndDate(deskId, date));

        //This loads the 'home.html' template to display the details
        mav.setViewName("home");

        //get desk object from modelMap and print to console
        //this is just to check all is working
        Map<String, Object> modelMap = mav.getModelMap();
        System.out.println(modelMap.get("booking").toString());

        return mav;
    }




}
