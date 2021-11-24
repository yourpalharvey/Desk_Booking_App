package com.bjss.desk_booking.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class adminController {



    @RequestMapping(path = "/admin/dashboard")
    public String adminDashboard(String name) {
        return "adminPanel";
    }

    @RequestMapping(path = "/admin/deskstatus")
    public String deskStatus(String name) {
        return "deskStatus";
    }

    @RequestMapping(path = "/admin/previousbooking")
    public String previousBooking(String name) {
        return "deskStatus";

    }
    @RequestMapping(path = "/admin/adddesk")
    public String addDesk(String name) {
        return "addDesk";
    }


}
