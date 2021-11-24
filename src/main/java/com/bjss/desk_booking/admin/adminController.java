package com.bjss.desk_booking.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class adminController {



    @RequestMapping(path = "/admin/dashboard")
    public String adminDashboard(String name) {
        return "adminPanel";
    }
}
