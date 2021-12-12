package com.bjss.desk_booking.office;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingDTO;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OfficeRestController {

    @Autowired
    OfficeService officeService;

    @PostMapping (value = "/public/getAllOffices")
    public String myOfficeStatus() {

        List<Office> officeList = officeService.findAll();
        String jsonString = JSONArray.toJSONString(officeList);
        return jsonString;
    }

}
