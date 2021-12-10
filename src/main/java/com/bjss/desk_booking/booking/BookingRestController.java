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

    @PostMapping(value = "/public/getUserBookingsByAdmin")
    public String getUserBookingsByAdmin(@RequestBody Map<String, String> username){
        String searchUser = username.get("username");
        User user = userService.findByUsername(searchUser);
        List<Booking> userBookingList = bookingService.findByUserId(user.getUserId());


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

    @DeleteMapping(value = "/public/cancelMyBooking")
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
        User currentUser = userService.getCurrentUser();

        // Create new booking, and add to database
        bookingService.save(new Booking(date, currentUser, deskToBook));
    }

    public boolean userHasBookingOnDate(User user, Date date){
        for(Booking b: bookingService.findByUserId(user.getUserId())){
            if(b.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    @PostMapping(value = "/public/loadDailyBookings")
    public String getDailyBookings(@RequestBody Map<String, String> bookingDetails){
        List<Booking> officeBookingListByDate = new ArrayList<>();
        List<BookingDTO> datedBookingDTOList = new ArrayList<>();


        //check through the user's bookings, if they have a booking for the given date,
        // then set disableButton to true to disable all booking buttons in user dashboard
        boolean userHasBooking = userHasBookingOnDate(userService.getCurrentUser(), Date.valueOf(bookingDetails.get("date")));

        System.out.println("USER HAS BOOKING: ------------------ " + userHasBooking);

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
            boolean disableButton = userHasBooking;

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
                    if(b.getDeskId() == d.getDeskId()){
                        cancelButton = true;
                    }
                }

                System.out.println("DISABLE BUTTON INNER BOOKING FOR LOOP : " + disableButton);
            }
            System.out.println("DISABLE BUTTON OUTER DESK FOR LOOP : " + disableButton);
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
        Date date = Date.valueOf(bookingDetails.get("date"));
        int currentUserId = userService.getCurrentUser().getUserId();

        //get lists of desks and bookings associated with the chosen office
        List<Desk> officeDeskList = deskService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));
        List<Booking> officeBookingList = bookingService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));

        //If the user already has a booking on the given date, return the booking details.
        boolean userHasBookingOnDate = userHasBookingOnDate(userService.getCurrentUser(), date);

        if(userHasBookingOnDate){
            for(Booking b: bookingService.findByUserId(currentUserId)){
                if(b.getDate().equals(date)){
                    BookingDTO existingBookingDTO = new BookingDTO(b.getDate().toString(),
                            b.getDeskId(), b.getOfficeName(), true);
                    try {
                        return objectMapper.writeValueAsString(existingBookingDTO);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //get a list of bookings for the specified date
        List<Booking> datedBookingList = new ArrayList<>();

        for (Booking b : officeBookingList) {
            if (b.getDate().equals(date)) {
                datedBookingList.add(b);
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
        User currentUser = userService.getCurrentUser();

        // Create new booking, and add to database
        Booking newBooking = new Booking(date, currentUser, randomDesk);
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


    public boolean fairPolicy(Booking booked)
    {
        List<Booking> bookingList=bookingService.findAll();
        int count=0;
        for (Booking booking:bookingList)
        {
            if(booking.getDate().equals(booked.getDate()))
            {
                count++; //find out number of booking on that day

            }

        }


        int bookedPercentage=(count/deskService.findAll().size())*100; //find the percentage of booked desk on that day
        if(bookedPercentage>=70)   //if more than 70% desk is booked it will check for user rating
        {
            for (Booking booking : bookingList) {
                if (booking.getUser().getUserId() == booked.getUser().getUserId()) {
                    if (!booking.isChecked()) { //check if user has any previous missed booking
                        booking.setChecked(true); //
                        booking.getUser().ratingDecrement(); //call for penalty function in the user rating
                        int rating = booking.getUser().getRating();
                        if (rating <= 70) {  //if rating is less than 70 then it will return false and need admin approval
                            return false;
                        } else {
                            return true;   //otherwise book the desk automatically
                        }



                    } else {
                        if (booking.getUser().getRating() <= 70) { //if the user has no previous missed booking still it will check for rating
                            return false;
                        } else {
                            return true;
                        }

                    }
                }

            }
        }
        return true;



    }




}
