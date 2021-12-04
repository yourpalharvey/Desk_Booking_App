package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BookingRestController {

    @Autowired
    BookingService bookingService;

    @Autowired
    DeskService deskService;

    @GetMapping(value = "/user/getMyBookings")
    public String myBookingStatus() {
        //currently hardcoded for userId = 1
        List<Booking> userBookingList = bookingService.findByUserId(1);
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for(Booking b : userBookingList){
            bookingDTOList.add(new BookingDTO(b.getBookingId(),b.getDate().toString(),b.getDeskId()));
        }

        System.out.println("HELLOOOOO");
        String jsonString = JSONArray.toJSONString(bookingDTOList);

        return jsonString;
    }

    @DeleteMapping(value = "/user/cancelMyBooking")
    public void cancelABooking(@RequestBody Map<String, Integer> bookingIdToCancel){
        System.out.println("INTEGER:");
        System.out.println(Integer.valueOf(bookingIdToCancel.get("bookingId")));

        bookingService.deleteById(bookingIdToCancel.get("bookingId"));
        //return myBookingStatus();
    }

    @PostMapping(value = "/user/loadDailyBookings")
    public String getDailyBookings(@RequestBody Map<String, Date> date){
        System.out.println("THIS IS THE POST MAPPING");
        System.out.println(date.get("date"));
        List<Booking> bookingListByDate = new ArrayList<>();
        List<BookingDTO> datedBookingDTOList = new ArrayList<>();

        //loop through all desks and bookings, and create a LinkedHashMap of desks both booked and unbooked
        //value in hashmap is true if booked, false if not booked (for that day)
        for (Booking b : bookingService.findAll()) {
            if (b.getDate().equals(date.get("date"))) {
                bookingListByDate.add(b);
                System.out.println(b.getDate());
            }
        }
        //loop through desks and bookings to see which desks are associated with bookings
        //add new BookingDTO to datedBookingDTOList including boolean attribute to show if booked

        for (Desk d: deskService.findAll()){
            boolean deskBooked = false;
            for(Booking b : bookingListByDate){
                if(b.getDesk().getDeskID() == d.getDeskID()){
                    deskBooked = true;
                    break;
                }
            }
            datedBookingDTOList.add(new BookingDTO(date.toString(),d.getDeskID(),deskBooked));
        }
        String jsonString = JSONArray.toJSONString(datedBookingDTOList);
        return jsonString;
    }

}
