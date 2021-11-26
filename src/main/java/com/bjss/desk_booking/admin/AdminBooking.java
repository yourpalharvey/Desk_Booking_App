package com.bjss.desk_booking.admin;


import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.desk.Desk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AdminBooking {


    @RequestMapping(path = "/admin/adminbooking")
    public String adminbooking(@ModelAttribute("booking") Booking booking,@RequestParam (required = false) String userId)
    {
        System.out.println(userId);
        return "Adminbooking";
    }
}
