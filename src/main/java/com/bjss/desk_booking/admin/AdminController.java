package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingService;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class AdminController {


    @Autowired
    DeskService deskService;

    @Autowired
    BookingService bookingService;


    @RequestMapping(path = "/admin/dashboard")
    public String adminDashboard(String name) {
        return "adminPanel";
    }



    @RequestMapping(path = "/previousbooking")
    public String previousBooking(Model model) {
        List<Booking> bookingList=bookingService.findAll();
        model.addAttribute("bookingList",bookingList);

        return "previousbooking";

    }

    @GetMapping("/previousbooking/{id}")
    public String deleteBooking(@PathVariable("id") int id, Model model) {
        bookingService.deleteById(id);


        return "redirect:/previousbooking";
    }



    @GetMapping("/admindeskstatus/{id}")
    public String deleteDesk(@PathVariable("id") int id, Model model) {
       deskService.deleteById(id);


        return "redirect:/admindeskstatus";
    }

    @GetMapping(value = "/admindeskstatus")
    public String deskstatus(Model model)

    {
        List<Desk> deskList=deskService.findAll();
        model.addAttribute("deskList", deskList);
        return "deskStatus";



    }



}
