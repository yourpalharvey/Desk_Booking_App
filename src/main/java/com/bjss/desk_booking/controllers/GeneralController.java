package com.bjss.desk_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

    @RequestMapping(path = "/")
    public String rootRedirect(String name) { return "home"; }

    @RequestMapping(path = "public/home")
    public String homePage(String name) { return "home"; }

    @RequestMapping(path = "admin/stuff")
    public String adminPage(String name) { return "admin"; }

    @RequestMapping(path = "user/booking")
    public String userPage(String name) { return "BookingPage"; }
}

