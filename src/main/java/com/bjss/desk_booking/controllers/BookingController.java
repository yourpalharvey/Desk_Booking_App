package com.bjss.desk_booking.controllers;

import com.bjss.desk_booking.repository.BookingRepository;
import com.bjss.desk_booking.repository.BookingRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class BookingController {

    private BookingRepositoryJDBC bookingRepo;

    @Autowired
    public BookingController(BookingRepositoryJDBC bookingRepo) {
        this.bookingRepo = bookingRepo;
    }


    //work out how to add parameters here
    @RequestMapping(path = "/user/hello", method = RequestMethod.GET)
//    public String hello(){
//        return "hello";
//    }
    public ModelAndView search(@RequestParam(value="name", defaultValue = "Desk 8") String name){
        ModelAndView mav = new ModelAndView();
        System.out.println(name);
        mav.addObject(bookingRepo.getDeskById(name));
        //This loads the 'home.html' template to display the details
        mav.setViewName("home");
        System.out.println("HELLO HELLO");
        System.out.println(mav);
        return mav;
    }




}
