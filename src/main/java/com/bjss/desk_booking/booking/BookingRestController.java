package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.office.OfficeService;
import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/user/getMyBookings")
    public String myBookingStatus() {
        //get all bookings by the current user
        List<Booking> userBookingList = bookingService.findByUserId(userService.getCurrentUser().getUserId());
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        //sort by date
        userBookingList.sort(Comparator.comparing(Booking::getDate));

        //get yesterdays date
        java.time.LocalDate localDate = java.time.LocalDate.now().minusDays(1);
        Date sqlDate = Date.valueOf(localDate);

        //filter the list to return only dates that are after yesterday
        userBookingList = userBookingList.stream().filter(booking -> booking.getDate()
                .after(sqlDate)).collect(Collectors.toList());

        //Create BookingDTOs from all bookings
        for(Booking b : userBookingList){
            bookingDTOList.add(new BookingDTO(b.getBookingId(),b.getDate().toString(),b.getDeskId(),b.getDesk().getDeskImageName(),b.getOfficeName()));
        }

        //return all user bookings as a list of BookingDTOs
        String jsonString = JSONArray.toJSONString(bookingDTOList);
        return jsonString;
    }

    @DeleteMapping(value = "/user/cancelMyBooking")
    public void cancelABooking(@RequestBody Map<String, Integer> bookingIdToCancel){
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
        User currentUser = userService.getCurrentUser();

        // Create new booking, and add to database
        bookingService.save(new Booking(date, currentUser, deskToBook));
    }

    @PostMapping(value = "/user/loadDailyBookings")
    public String getDailyBookings(@RequestBody Map<String, String> bookingDetails){
        List<Booking> officeBookingListByDate = new ArrayList<>();
        List<BookingDTO> datedBookingDTOList = new ArrayList<>();

        //loop through all desks and bookings for the chosen office,
        // create an ArrayList of all desks both booked and unbooked (datedBookingDTOList)
        // values in bookingDetails Map are strings, so must cast strings to date and int
        for (Booking b : bookingService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")))) {
            if (b.getDate().equals(Date.valueOf(bookingDetails.get("date")))) {
                officeBookingListByDate.add(b);
            }
        }

        //loop through desks and bookings for chosen office to see which desks are associated with bookings
        //add new BookingDTO to datedBookingDTOList including boolean attribute to show if booked
        for (Desk d: deskService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")))){
            boolean deskBooked = false;
            boolean disableButton = false;
            String userBooked = "";
            boolean cancelButton = false;
            int bookingId = 0;


            for(Booking b : officeBookingListByDate){
                if(b.getDesk().getDeskId() == d.getDeskId()){
                    deskBooked = true;
                    userBooked = b.getUser().getUsername();
                    bookingId = b.getBookingId();
                }
                //check if userId from booking is same as currentUser's userId
                if(b.getUserId() == userService.getCurrentUser().getUserId()){
                    disableButton = true;
                    if(b.getDeskId() == d.getDeskId()){
                        cancelButton = true;
                    }
                }
            }

            datedBookingDTOList.add(new BookingDTO(bookingId, bookingDetails.get("date"),d.getDeskId()
                    ,deskBooked,d.getDeskImageName(),d.getOffice().getOfficeName()
                    ,d.getMonitorOption(),d.getDeskPosition(),d.getDeskType(), userBooked, disableButton, cancelButton));
        }
        //return json list of all office desks
        String jsonString = JSONArray.toJSONString(datedBookingDTOList);

        //print current user's username to the console (for testing)
        System.out.println(userService.getCurrentUser().getUsername());

        return jsonString;
    }

    @PostMapping(value = "/user/createQuickBooking")
    public String createQuickBooking(@RequestBody Map<String, String> bookingDetails){

        //get lists of desks and bookings associated with the chosen office
        List<Desk> officeDeskList = deskService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));
        List<Booking> officeBookingList = bookingService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));

        //get a list of bookings for the specified date
        List<Booking> datedBookingList = new ArrayList<>();

        for (Booking b : officeBookingList) {
            if (b.getDate().equals(Date.valueOf(bookingDetails.get("date")))) {
                datedBookingList.add(b);
            }
        }

        //todo - fix multiple bookings on same day at different offices
        for (Booking b : datedBookingList){
            if(b.getUserId() == userService.getCurrentUser().getUserId()){
                BookingDTO existingBookingDTO = new BookingDTO(b.getDate().toString(),
                        b.getDeskId(), b.getOfficeName(), true);
                try {
                    return objectMapper.writeValueAsString(existingBookingDTO);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }

        //return empty JSON string if all desks are booked for that day
        if (officeDeskList.size() == datedBookingList.size()) {
            System.out.println("ERROR - ALL DESKS FULL!!!!");
            List<Booking> emptyList = new ArrayList<>();
            String jsonString = JSONArray.toJSONString(emptyList);
            return jsonString;
        }

        // Create random integer between 0 and the size of officeDeskList
        Random random = new Random();
        int randomInt = random.nextInt(officeDeskList.size());
        System.out.println("randomInt 1: " + randomInt);

        //  loop through datedBookingList,
        // check whether the deskId at index=[randomInt] already has a booking for that date
        // if it does, chose another randomInt and restart the loop by setting j = 0
        // if the loop completes, we can assume there is no booking for that desk on specified date
        for(int j = 0; j < datedBookingList.size(); j++){
            if(officeDeskList.get(randomInt).getDeskId() == datedBookingList.get(j).getDeskId()){
                randomInt = random.nextInt(officeDeskList.size());
                System.out.println("randomInt 2: " + randomInt);
                j = -1;
            }
        }

        //once the loop exits, get the desk object from officeDeskList at index=[randomInt]
        Desk randomDesk = officeDeskList.get(randomInt);

        // Set current user for booking
        // **** CURRENTLY HARDCODED TO USER WITH USERID = 1; **** //
        User currentUser = userService.getCurrentUser();

        // Create new booking, and add to database
        Booking newBooking = new Booking(Date.valueOf(bookingDetails.get("date")), currentUser, randomDesk);
        bookingService.save(newBooking);

        // Create new BookingDTO object to pass back to javascript as JSON
        BookingDTO newBookingDTO = new BookingDTO(newBooking.getDate().toString(),
                newBooking.getDeskId(), newBooking.getOfficeName());

        // Map newBookingDTO to a json string and return it
        String jsonString = "";

        try {
            jsonString = objectMapper.writeValueAsString(newBookingDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
        return jsonString;
    }



}
