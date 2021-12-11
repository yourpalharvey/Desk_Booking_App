package com.bjss.desk_booking.desk;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingDTO;
import com.bjss.desk_booking.booking.BookingService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DeskRestController {

    @Autowired
    DeskService deskService;

    @Autowired
    BookingService bookingService;

    @PostMapping(path = "/admin/viewDesksByOffice")
    public String viewDesks(@RequestBody Map<String, Integer> officeId){
        //create a list of desk transfer objects (by office) to return as json
        System.out.println("OFFICE Id: ----------- " + officeId.get("officeId"));
        List<Desk> deskList = deskService.findAllByOfficeId(officeId.get("officeId"));
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for(Desk d: deskList){
            System.out.println(d.getOffice());
            deskDTOList.add(new DeskDTO(d.getDeskId(),d.getDeskName(),d.getDeskType()
                    ,d.getDeskPosition(),d.getMonitorOption(),d.getDeskStatus(),d.getDeskImageName(),d.getOffice().getOfficeName()));
        }

        return JSONArray.toJSONString(deskDTOList);
    }

    //delete desk and it's associated bookings
    @DeleteMapping(path = "/admin/deleteDesk")
    public String deleteDesk(@RequestBody Map<String, Integer> deskToDeleteMap){
        int deskIdToDelete = deskToDeleteMap.get("deskId");

        //get a list of bookings that have been made for the desk id to delete
        List<Booking> bookingsForDelete = bookingService.findAllByDeskId(deskIdToDelete);

        //get a list of bookingDTOs to send back to the front end
        List<BookingDTO> deletedBookingDTOList = new ArrayList<>();
        bookingsForDelete.forEach(
                b -> deletedBookingDTOList.add(
                        new BookingDTO(b.getDate().toString()
                                ,b.getUser().getUsername()
                                ,b.getDeskId())
        ));

        //delete each booking for the desk
        bookingsForDelete.forEach(b -> bookingService.deleteById(b.getBookingId()));

        //delete the desk
        deskService.deleteById(deskIdToDelete);

        String jsonString = JSONArray.toJSONString(deletedBookingDTOList);
        return jsonString;
    }




}
