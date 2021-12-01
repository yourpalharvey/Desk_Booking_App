package com.bjss.desk_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

    @RequestMapping(path = "/")
    public String rootRedirect(String name) {
        return "home";
    }

    @RequestMapping(path = "/public/home")
    public String homeStuff(String name) {
        return "home";
    }

    @RequestMapping(path = "/user/dashboard")
    public String userDashboard(String name) {
        return "BookingPage";
    }

    //Test site
    @RequestMapping(path = "/user/dashboardtwo")
    public String userDashboardTwo(String name) {
        return "BookingPageTwo";
    }

    @RequestMapping(path = "/user/quickbooking")
    public String userQuickBooking(String name) {
        return "QuickBookingPage";
    }

    @RequestMapping(path = "/user/mybookings")
    public String userBookings(String name) {
        return "MyBookingPage";
    }

}

