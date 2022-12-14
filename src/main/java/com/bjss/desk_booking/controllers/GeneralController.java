package com.bjss.desk_booking.controllers;

import org.springframework.stereotype.Controller;
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

    @RequestMapping(path = "/user/quickbooking")
    public String userQuickBooking(String name) {
        return "QuickBookingPage";
    }

    @RequestMapping(path = "/user/mybookings")
    public String userBookings(String name) {
        return "MyBookingPage";
    }

    @RequestMapping(path = "/admin/deskstatus")
    public String deskStatus(String name) { return "deskStatus2";};

    @RequestMapping(path= "/admin/pending")
    public String pendingBookings(String name) { return "PendingBooking2";}

    @RequestMapping(path = "/admin/comment")
    public String adminComment(String name) {
        return "adminComment";
    }

    @RequestMapping(path = "/user/comment")
    public String userComment(String name){return "userComment";}

    @RequestMapping(path = "/admindashboard")
    public String adminDash(String name){return "adminpanel";}
}


