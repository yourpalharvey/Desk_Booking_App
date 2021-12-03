

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


    // display user bookings on the mybookings screen - currently hardcoded to display all bookings
    // todo - update this method to only return bookings with user_id of current user (if we have time)
    @GetMapping(value = "/user/myBookings")
    public String myBookingStatus(Model model) {
        List<Booking> bookingList = bookingService.findAll();
        model.addAttribute("bookingList", bookingList);
        return "MyBookingPage";
    }

    // display bookings based on the date chosen in calendar on user dashboard
    @GetMapping(value = "/user/getBookingsByDate")
    public String bookingStatus(
            Model model,
            @RequestParam Date date
    ) {
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
                if (b.getDesk().getDeskID() == d.getDeskID()) {
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
        return "BookingPageDated";
    }

    // Create a quick booking for a random desk
    // todo - this should be be changed to choose the worst desks first once we sort desk attributes out in db
    // todo - we could also add a couple of checkboxes under the quick booking in case the user wants standing desk

    @PostMapping(value = "user/quickBooking")
    public String addQuickBooking(@RequestParam Date date,
                                  Model model) {
        //get lists of bookings and desks
        List<Desk> deskList = deskService.findAll();
        List<Booking> bookingList = bookingService.findAll();

        //get list of bookings for the 'date' parameter
        //if number of desks = number of bookings for that day, return an error page
        List<Booking> datedBookingList = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.getDate().equals(date)) {
                datedBookingList.add(b);
            }
        }
        // TODO - make this actually return some kind of error page
        if (deskList.size() == datedBookingList.size()) {
            System.out.println("ERROR - ALL DESKS FULL!!!!");
            model.addAttribute("fulldesk", true);
            return "QuickBookingPage";

        }


        // set random desk object for booking
        Random random = new Random();
        int randomInt = random.nextInt(deskList.size()) + 1;
        System.out.println("randomInt 1: " + randomInt);

        //loop through bookings for that date and compare IDs to make sure not to duplicate desks
        //if the desk is already booked that day, get another random int and restart the loop
        int count = 0;
        while (count < deskList.size()) {
            for (Booking b : bookingList) {
                if (b.getDesk().getDeskID() == randomInt && b.getDate().equals(date)) {
                    randomInt = random.nextInt(deskList.size()) + 1;
                    System.out.println("randomInt 2: " + randomInt);
                    count = 0;
                    break;
                }
            }
            count++;
        }


        Desk randomDesk = deskService.findById(randomInt);

        // Set current user for booking
        // **** CURRENTLY HARDCODED TO USER WITH USERID = 1; **** //
        User currentUser = userService.findById(1);

        // Create new booking, and add to database
        bookingService.save(new Booking(date, currentUser, randomDesk));

        model.addAttribute("deskBooked", randomInt);
        return "QuickBookingPage";
    }


    //todo - need to refine this with a helper method because currently I have repeated lots of code
    @PostMapping(value = "user/bookDesk")
    public String addQuickBooking(@RequestParam Date date,
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
                if (b.getDesk().getDeskID() == d.getDeskID()) {
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
        return "BookingPageDated";


    }

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
                if (b.getDesk().getDeskID() == d.getDeskID()) {
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
                if (b.getDesk().getDeskID() == d.getDeskID()) {
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