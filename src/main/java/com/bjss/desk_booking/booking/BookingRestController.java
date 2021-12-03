package com.bjss.desk_booking.booking;


import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONArray;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookingRestController {

    @Autowired
    BookingService bookingService;

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
    public void cancelABooking(@RequestBody Map<?, ?> bookingId){



        System.out.println(bookingId);

//        bookingService.deleteById(bookingId);
//        myBookingStatus();
    }

}
