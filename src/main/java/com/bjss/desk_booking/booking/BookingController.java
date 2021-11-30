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

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Random;

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
    @GetMapping(value = "user/getBookingsByDate")
    public String bookingStatus(
            Model model,
            @RequestParam Date date
            ) {
        // get all bookings from database, and create a new list of all bookings with date given in parameter
        List<Booking> bookingListByDate = new ArrayList<>();

        for (Booking b: bookingService.findAll()) {
            if (b.getDate().equals(date)){
                bookingListByDate.add(b);
            }
        }
        //print to console - testing purposes only
        for (Booking b: bookingListByDate){
            System.out.println(b);
        }

        model.addAttribute("bookingListByDate", bookingListByDate);
        return "BookingPage";
    }

    // Create a quick booking for a random desk
    // todo - this should be be changed to choose the worst desks first once we sort desk attributes out in db
    // todo - we could also add a couple of checkboxes under the quick booking in case the user wants standing desk

    @PostMapping(value = "user/quickBooking")
    public String addQuickBooking(@RequestParam Date date){

        // set random desk object for booking
        Random random = new Random();
        int randomInt = random.nextInt(deskService.findAll().size()) + 1;
        Desk randomDesk = deskService.findById(randomInt);

        // Set current user for booking
        // **** CURRENTLY HARDCODED TO USER WITH USERID = 1; **** //
        User currentUser = userService.findById(1);

        // Create new booking, and add to database
        bookingService.save(new Booking(date, currentUser, randomDesk));

        return "MyBookingPage";
    }
}