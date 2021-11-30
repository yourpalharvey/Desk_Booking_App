package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    DeskService deskService;

    // display bookings on the mybookings screen
    // todo - update this method to only return bookings with user_id of current user
    @GetMapping(value = "/user/mybookings")
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
        List<Booking> bookingList = bookingService.findAll();
        List<Booking> bookingListByDate = new ArrayList<>();
        System.out.println(date);

        for (Booking b: bookingList) {
            if (b.getDate() == date){
                bookingListByDate.add(b);
            }
            System.out.println(b);
        }

        model.addAttribute("bookingListByDate", bookingListByDate);
        return "BookingPage";

    }

    // Create a quick booking

    //@PostMapping
}
