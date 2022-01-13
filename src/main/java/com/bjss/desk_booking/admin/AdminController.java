package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingService;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller     //Defining it is a Controller class of spring boot application
public class AdminController {


    @Autowired //Injecting DeskService Class
    DeskService deskService;

    @Autowired //Injecting BookingService Class
    BookingService bookingService;

/*HTTP Request mapping for admin dashboard page */
    @RequestMapping(path = "/admindashboard")
    public String adminDashboard(Model model) {

        List<Booking> bookingList = bookingService.findAll();
        model.addAttribute("bookingList", bookingList);

        return "adminPanel";
    }


    /*Controller for showing all the previous booking*/
    @RequestMapping(path = "/previousbooking")
    public String previousBooking(Model model) {

        Date date = new Date(); //Storing Today's date in date variable
        List<Booking> allbookingList = bookingService.findAll();
        List<Booking> bookingList = new ArrayList<>();
        for (Booking b : allbookingList) {
            if (b.getDate().after(date) || b.getDate().equals(date))   //Finding out all the booking which are scheduled today or on any future date
            {
                bookingList.add(b);

            }
        }
        model.addAttribute("bookingList", bookingList);

        return "previousbooking";

    }
    /*HTTP GEt Request mapping for deleting a booking using the id passed as path variable from client side server  */
    @GetMapping("/previousbooking/{id}")
    public String deleteBooking(@PathVariable("id") int id, Model model) {
        bookingService.deleteById(id);

        return "redirect:/previousbooking";
    }

    /*HTTP GEt Request mapping for deleting a desk using the id passed as path variable from client side server  */
    @GetMapping("/admindeskstatus/{id}")
    public String deleteDesk(@PathVariable("id") int id, Model model) {
        deskService.deleteById(id);

        return "redirect:/admindeskstatus";
    }

    /*HTTP GEt Request mapping for showing all desk   */
    @GetMapping(value = "/admineskstatus")
    public String deskStatus(Model model) {
        List<Desk> deskList = deskService.findAll();
        model.addAttribute("deskList", deskList);

        return "deskStatus";


    }

    /*HTTP GEt Request mapping for showing all booking according to date  */
    @GetMapping(value = "/adminGetBookingsByDate")
    public String bookingByDate(Model model, @RequestParam(required = false) java.sql.Date date) {
        if (date == null)
        {

            return "previousbooking";
        }

        List<Booking> allBookingList = bookingService.findAll();
        List<Booking> bookingList = new ArrayList<>();
        for (Booking b : allBookingList) {
            if (b.getDate().equals(date))  //Search any booking by date taken from input from client server.
            {
                bookingList.add(b);
            }
        }
        model.addAttribute("bookingList", bookingList);

        return "adminPanelDated";


    }
    /*HTTP GEt Request mapping for showing all booking according to User Name  */
    @GetMapping(value = "/admingetBookingsByUserName")
    public String bookingByUser(Model model, @RequestParam(required = false) java.sql.Date date, String name) {

        List<Booking> allbookingList = bookingService.findAll();
        List<Booking> bookingList = new ArrayList<>();
        for (Booking b : allbookingList) {
            if (b.getUser().getUsername().equals(name))  //Search by name
            {
                bookingList.add(b);
            }

        }
        model.addAttribute("bookingList", bookingList);
        
        return "adminPanelDated";


    }


}
