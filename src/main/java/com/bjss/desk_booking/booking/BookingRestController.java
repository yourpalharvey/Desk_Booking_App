package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.office.OfficeService;
import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.user.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class BookingRestController {

    @Autowired
    BookingService bookingService;

    @Autowired
    DeskService deskService;

    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @GetMapping(value = "/user/getMyBookings")
    public String myBookingStatus() {
        //currently hardcoded for userId = 1
        List<Booking> userBookingList = bookingService.findByUserId(1);
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for(Booking b : userBookingList){
            bookingDTOList.add(new BookingDTO(b.getBookingId(),b.getDate().toString(),b.getDeskId(),b.getDesk().getDeskImageName()));
        }

        String jsonString = JSONArray.toJSONString(bookingDTOList);

        return jsonString;
    }

    @DeleteMapping(value = "/user/cancelMyBooking")
    public void cancelABooking(@RequestBody Map<String, Integer> bookingIdToCancel){

        //get the details of the booking about to be cancelled
        Booking b = bookingService.findById(bookingIdToCancel.get("bookingId"));

        //delete the booking
        bookingService.deleteById(bookingIdToCancel.get("bookingId"));
    }

    @PostMapping(value = "/user/makeBooking")
    public void makeABooking(@RequestBody Map<String, String> newBookingDetails){
        Date date = Date.valueOf(newBookingDetails.get("date"));
        int deskId = Integer.parseInt(newBookingDetails.get("deskId"));

        //set Desk object for booking
        Desk deskToBook = deskService.findById(deskId);

        // Set current user for booking
        // **** CURRENTLY HARDCODED TO USER WITH USERID = 1; **** //
        User currentUser = userService.findById(1);

        // Create new booking, and add to database
        bookingService.save(new Booking(date, currentUser, deskToBook));

    }

    @PostMapping(value = "/user/loadDailyBookings")
    public String getDailyBookings(@RequestBody Map<String, Date> date){
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
            datedBookingDTOList.add(new BookingDTO(date.toString(),d.getDeskID(),deskBooked,d.getDeskImageName()));
        }
        String jsonString = JSONArray.toJSONString(datedBookingDTOList);
        return jsonString;
    }

    @PostMapping(value = "/user/createQuickBooking")
    public String createQuickBooking(@RequestBody Map<String, String> bookingDetails){
        List<Desk> deskList = deskService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeLocation")));
        System.out.println(bookingDetails.get("officeLocation"));
        List<Booking> bookingList = bookingService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeLocation")));

        //get list of bookings for the 'date' parameter
        //if number of desks = number of bookings for that day, return an error page
        List<Booking> detailedBookingList = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.getDate().equals(Date.valueOf(bookingDetails.get("date"))))
//                    && Integer.parseInt((String) bookingDetails.get("officeId")) == b.getDesk().getOffice().getOfficeId())
            {
                detailedBookingList.add(b);
            }
        }
        // TODO - make this actually return some kind of error page

        //return empty JSON string if all desks are booked for that day
        if (deskList.size() == detailedBookingList.size()) {
            System.out.println("ERROR - ALL DESKS FULL!!!!");
            List<Booking> emptyList = new ArrayList<>();
            String jsonString = JSONArray.toJSONString(emptyList);
            System.out.println(jsonString);
            return jsonString;

        }

        // set random desk object for booking
        Random random = new Random();
        int randomInt = random.nextInt(deskList.size()) + 1;
        System.out.println("randomInt 1: " + randomInt);

        //loop through bookings for that date and compare IDs to make sure not to duplicate desks
        //if the desk is already booked that day, get another random int and restart the loop

        //TODO - FIX THIS METHOD
        int count = 0;
        while (count < deskList.size()) {
            for (int i = 0; i<detailedBookingList.size();i++) {
                if (detailedBookingList.get(i).getDate().equals(Date.valueOf(bookingDetails.get("date")))
                        && detailedBookingList.get(i).getDeskId() == deskList.get(count).getDeskID()) {
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
        Booking newBooking = new Booking(Date.valueOf(bookingDetails.get("date")), currentUser, randomDesk);

        // Create new BookingDTO object to pass back to javascript as JSON
        List<BookingDTO> newBookingDTO = new ArrayList<>();
        newBookingDTO.add(new BookingDTO(officeService.findById(Integer.parseInt(bookingDetails.get("officeLocation"))).getOfficeName(), newBooking.getBookingId(), bookingDetails.get("date").toString(), randomInt));
        bookingService.save(newBooking);

        String jsonString = JSONArray.toJSONString(newBookingDTO);
        System.out.println(jsonString);
        return jsonString;
    }



}
