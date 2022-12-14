package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.email.EmailSender;
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

    @Autowired
    EmailSender emailSender;

    @Autowired
    FairPolicy fairPolicy;

    @PostMapping(value = "/user/bookingCheckIn")
    public String bookingCheckIn(@RequestBody Map<String, Integer> bookingIdMap){
        int bookingId = bookingIdMap.get("bookingId");
        Booking booking=bookingService.findById(bookingId);
        booking.setChecked(true);
        booking.getUser().ratingIncrement();
        bookingService.save(booking);
        try
        {
            emailSender.checkinEmailSender(booking);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
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
            bookingDTOList.add(new BookingDTO(
                    b.getBookingId(),
                    b.getDate().toString(),
                    b.getDeskId(),
                    b.getDesk().getDeskImageName(),
                    b.getOfficeName(),
                    b.isChecked()));
        }

        //return all user bookings as a list of BookingDTOs
        String jsonString = JSONArray.toJSONString(bookingDTOList);
        return jsonString;
    }

    //this method is public because it can be used by either an admin or a user
    @DeleteMapping(value = "/user/cancelMyBooking")
    public void cancelABooking(@RequestBody Map<String, Integer> bookingIdToCancel){
        //delete the booking

        Booking booking = bookingService.findById(bookingIdToCancel.get("bookingId"));
        try {
            emailSender.cancelledEmailSender(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        Booking booking=new Booking(date,currentUser,deskToBook);

        if(!fairPolicy.fairPolicy(booking))
        {
           booking.setApproved(false);
           bookingService.save(booking);
            bookingService.save(booking);
            try {
                emailSender.pendingEmailSender(booking);;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
           booking.setApproved(true);
           bookingService.save(booking);
            try {
                emailSender.confirmEmailSender(booking);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //helper function to show if a user already has a booking on given date
    public boolean userHasBookingOnDate(User user, Date date){
        for(Booking b: bookingService.findByUserId(user.getUserId())){
            if(b.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    //public method as this is also used for the admin to load bookings
    @PostMapping(value = "/public/loadDailyBookings")
    public String getDailyBookings(@RequestBody Map<String, String> bookingDetails){
        List<Booking> officeBookingListByDate = new ArrayList<>();
        List<BookingDTO> datedBookingDTOList = new ArrayList<>();

        //check through the user's bookings, if they have a booking for the given date,
        // then set disableButton to true to disable all booking buttons in user dashboard
        boolean userHasBooking = userHasBookingOnDate(userService.getCurrentUser(), Date.valueOf(bookingDetails.get("date")));

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
            }
            //create new list of booking DTOs to return to the frontend to display
            datedBookingDTOList.add(new BookingDTO(
                    bookingId,
                    bookingDetails.get("date"),
                    d.getDeskId(),
                    deskBooked,
                    d.getDeskImageName(),
                    d.getOffice().getOfficeName(),
                    d.getMonitorOption(),
                    d.getDeskPosition(),
                    d.getDeskType(),
                    userBooked,
                    disableButton,
                    cancelButton));
        }
        //return json list of all office desks
        String jsonString = JSONArray.toJSONString(datedBookingDTOList);

        return jsonString;
    }

    @PostMapping(value = "/user/createQuickBooking")
    public String createQuickBooking(@RequestBody Map<String, String> bookingDetails){
        //get date and current user
        Date date = Date.valueOf(bookingDetails.get("date"));
        int currentUserId = userService.getCurrentUser().getUserId();

        //get lists of desks and bookings associated with the chosen office
        List<Desk> officeDeskList = deskService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));
        List<Booking> officeBookingList = bookingService.findAllByOfficeId(Integer.parseInt(bookingDetails.get("officeId")));

        //Check if user has booking on the given date
        boolean userHasBookingOnDate = userHasBookingOnDate(userService.getCurrentUser(), date);

        //if user has a booking already for that date, return the details of that booking
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

        //test against fair policy
        if(!fairPolicy.fairPolicy(newBooking))
        {
            newBooking.setApproved(false);
            bookingService.save(newBooking);
            try {
                emailSender.pendingEmailSender(newBooking);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            newBooking.setApproved(true);
            bookingService.save(newBooking);

            try {
                emailSender.confirmEmailSender(newBooking);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

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




