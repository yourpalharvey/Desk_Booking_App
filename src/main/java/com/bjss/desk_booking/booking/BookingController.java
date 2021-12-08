

package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.sql.Date;

@Controller
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    DeskService deskService;

    @Autowired
    UserService userService;

    // display bookings based on the date chosen in calendar on admin dashboard
    @GetMapping(value = "/admin/getBookingsByDate")
    public String bookingCancelStatus(
            Model model,
            @RequestParam Date date
    ) {
        // get all bookings from database, and create a new list of all bookings with date given in parameter
        List<Booking> bookingCancelListByDate = new ArrayList<>();

        for (Booking b : bookingService.findAll()) {
            if (b.getDate().equals(date)) {
                bookingCancelListByDate.add(b);
            }
        }

        //loop through all desks and bookings, and create a LinkedHashMap of desks both booked and unbooked
        //value in hashmap is true if booked, false if not booked (for that day)
        Map<Desk, Boolean> allDeskMap = new LinkedHashMap<>();

        //loop through desks and bookings to see which desks are associated with bookings
        //add Desk to hashmap as a key, with boolean as value
        for (Desk d : deskService.findAll()) {
            boolean deskBooked = false;
            for (Booking b : bookingCancelListByDate) {
                if (b.getDesk().getDeskId() == d.getDeskId()) {
                    deskBooked = true;
                    break;
                }
            }
            allDeskMap.put(d, deskBooked);
        }

        //print to console - testing purposes only
        for (Desk d : allDeskMap.keySet()) {
            System.out.println(d);
            System.out.println(allDeskMap.get(d));
        }

        model.addAttribute("allDeskMap", allDeskMap);
        model.addAttribute("viewingDate", date);
        return "adminPanelDated";
    }

    //todo - need to refine this with a helper method because currently I have repeated lots of code
    @PostMapping(value = "admin/cancelDeskBooking")
    public String cancelBooking(@RequestParam Date date,
                                  @RequestParam int deskId,
                                  Model model) {
        Desk deskToBook = deskService.findById(deskId);

        //user is currently hardcoded to userId '1'
        User currentUser = userService.findById(1);

        //create booking
        bookingService.save(new Booking(date, currentUser, deskToBook));

        // get all bookings from database, and create a new list of all bookings with date given in parameter
        List<Booking> bookingListByDate = new ArrayList<>();

        for (Booking b : bookingService.findAll()) {
            if (b.getDate().equals(date)) {
                bookingListByDate.add(b);
            }
        }

        //loop through all desks and bookings, and create a LinkedHashMap of desks both booked and unbooked
        //value in hashmap is true if booked, false if not booked (for that day)
        Map<Desk, Boolean> allDeskMap = new LinkedHashMap<>();

        //loop through desks and bookings to see which desks are associated with bookings
        //add Desk to hashmap as a key, with boolean as value
        for (Desk d : deskService.findAll()) {
            boolean deskBooked = false;
            for (Booking b : bookingListByDate) {
                if (b.getDesk().getDeskId() == d.getDeskId()) {
                    deskBooked = true;
                    break;
                }
            }
            allDeskMap.put(d, deskBooked);
        }

        //print to console - testing purposes only
        for (Desk d : allDeskMap.keySet()) {
            System.out.println(d);
            System.out.println(allDeskMap.get(d));
        }

        model.addAttribute("allDeskMap", allDeskMap);
        model.addAttribute("viewingDate", date);
        return "adminPanelDated";


    }
}