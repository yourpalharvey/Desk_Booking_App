package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingDTO;
import com.bjss.desk_booking.booking.BookingService;
import com.bjss.desk_booking.email.EmailSender;
import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class AdminRestController {

    @Autowired
    BookingService bookingService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    EmailSender emailSender;

    // cancel booking from admin side
    @PostMapping("/admin/cancelPending")
    public String cancelBooking(@RequestBody Map<String, Integer> bookingToCancelMap) {

        //get booking objects by the ID given in request body
        int id = bookingToCancelMap.get("bookingId");

        Booking booking=bookingService.findById(id);
        try {
            emailSender.cancelEmailSender(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingService.deleteById(id);

        //create PendingBookingDTO object to return to the front end to display
        PendingBookingDTO cancelledBooking = new PendingBookingDTO(
                booking.getBookingId(),
                booking.getDate().toString(),
                booking.getDeskId(),
                booking.getDesk().getDeskImageName(),
                booking.getOfficeName(),
                booking.getUser().getUsername()
        );

        try {
            return objectMapper.writeValueAsString(cancelledBooking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // approve a pending booking from admin side
    @PostMapping("/admin/approvePending")
    public String acceptBooking(@RequestBody Map<String, Integer> bookingToAcceptMap) {
        int id = bookingToAcceptMap.get("bookingId");

        Booking booking=bookingService.findById(id);
        booking.setApproved(true);
        try {
            emailSender.confirmationEmailSender(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingService.save(booking);

        //create new PendingBookingDTO object to return to the frontend
        PendingBookingDTO approvedBooking = new PendingBookingDTO(
                booking.getBookingId(),
                booking.getDate().toString(),
                booking.getDeskId(),
                booking.getDesk().getDeskImageName(),
                booking.getOfficeName(),
                booking.getUser().getUsername()
        );

        try {
            return objectMapper.writeValueAsString(approvedBooking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //get all pending bookings to display in the 'pending booking' section
    @PostMapping("/admin/getAllPending")
    public String pendingBooking(){

        List<Booking> allBookingList=bookingService.findAll();

        //sort allBookingList by date
        allBookingList.sort(Comparator.comparing(Booking::getDate));

        List<PendingBookingDTO> pendingBookingList=new ArrayList<>();
        Date date = new Date();

        //check through list of all bookings, if the booking requires approval, create new PendingBookingDTO to be
        //returned to front end
        for (Booking b:allBookingList){
            //If the booking requires approval
            if(!b.isApproved() && b.getDate().after(date) || b.getDate().equals(date)) {
                PendingBookingDTO pendingBookingDTO = new PendingBookingDTO(
                        b.getBookingId(),
                        b.getDate().toString(),
                        b.getDeskId(),
                        b.getDesk().getDeskImageName(),
                        b.getOfficeName(),
                        b.getUser().getUsername());
                pendingBookingList.add(pendingBookingDTO);
            }
        }

        String jsonString = JSONArray.toJSONString(pendingBookingList);

        return jsonString;
    }

    // Get all bookings associated with the User searched for
    @PostMapping(value = "/admin/getUserBookingsByAdmin")
    public String getUserBookingsByAdmin(@RequestBody Map<String, String> username){
        //get the username from the request body
        String searchUser = username.get("username");
        User user = userService.findByUsername(searchUser);
        List<Booking> userBookingList = bookingService.findByUserId(user.getUserId());

        //sort by date
        userBookingList.sort(Comparator.comparing(Booking::getDate));
        List<BookingDTO> userBookingDTOList = new ArrayList<>();

        for(Booking b: userBookingList){
            userBookingDTOList.add(new BookingDTO(b.getBookingId(), b.getDate().toString(),b.getDeskId()
                    ,true,b.getDesk().getDeskImageName(),b.getDesk().getOffice().getOfficeName()
                    ,b.getDesk().getMonitorOption(),b.getDesk().getDeskPosition()
                    ,b.getDesk().getDeskType(), b.getUser().getUsername(), false, true));
        }

        String jsonString = JSONArray.toJSONString(userBookingDTOList);
        return jsonString;
    }

    @DeleteMapping(value = "/admin/cancelBooking")
    public void cancelABooking(@RequestBody Map<String, Integer> bookingIdToCancel){
        //delete the booking

        Booking booking = bookingService.findById(bookingIdToCancel.get("bookingId"));
        try {
            emailSender.cancelEmailSender(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingService.deleteById(bookingIdToCancel.get("bookingId"));

    }

}
