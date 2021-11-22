package com.bjss.desk_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class bookingController {
   @RequestMapping(path="home")
    public String home(){
        return "home";
    }
}
