package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.*;

@RestController
public class AdminRestController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    BookingService bookingService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/admin/cancelPending")
    public String cancelBooking(@RequestBody Map<String, Integer> bookingToCancelMap) {

        int id = bookingToCancelMap.get("bookingId");

        Booking booking=bookingService.findById(id);
        try {
            cancelEmailSender(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingService.deleteById(id);

        //create PendingBookingDTO object to return to the front end

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

    @PostMapping("/admin/approvePending")
    public String acceptBooking(@RequestBody Map<String, Integer> bookingToAcceptMap) {
        int id = bookingToAcceptMap.get("bookingId");

        Booking booking=bookingService.findById(id);
        booking.setApproved(true);
        try {
            confirmationEmailSender(booking);
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
            if(!b.isApproved() && b.getDate().after(date) ||b.getDate().equals(date)) {
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

    //the below is a copy of the email sender functions from AdminController.
    //todo - the below functions would be better off in their own class so that they can be used in multiple places

    public void cancelEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Desk Booking information");
            message.setText("We are very sorry to say that your booking request (booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate()+"\nhas been canceled.Sorry for the inconvenience");
            mailSender.send(message);

        }

    }



    public void confirmationEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Desk Booking Confirmation");
            message.setText("Your booking(booking ID:"+booking.getBookingId()+"\tBooking Date: "+booking.getDate()+" is confirmed \nThis is a "+booking.getDesk().getDeskType()+"\nThe Desk ID is: "+booking.getDesk().getDeskId()
                    +"The location of the Desk is: "+booking.getDesk().getDeskPosition()+"\nIt has "+booking.getDesk().getMonitorOption()+" monitors");
            mailSender.send(message);

        }

    }


}
