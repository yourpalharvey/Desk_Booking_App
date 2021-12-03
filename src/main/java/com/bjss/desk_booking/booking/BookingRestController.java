package com.bjss.desk_booking.booking;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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


}
