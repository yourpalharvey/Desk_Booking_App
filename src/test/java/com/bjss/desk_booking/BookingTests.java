package com.bjss.desk_booking;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingRestController;
import com.bjss.desk_booking.booking.BookingService;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.office.Office;
import com.bjss.desk_booking.office.OfficeService;
import com.bjss.desk_booking.user.User;
import com.bjss.desk_booking.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class BookingTests {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private DeskService deskService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRestController bookingRestController;

    private Office office;
    private Desk desk;
    private User user;


    @BeforeAll
    public void createTempObjects(){
        //create temporary objects and save to temporary db (ids are autogenerated so will be 1)
        office = new Office();
        office.setOfficeName("Bristol");
        officeService.save(office);

        desk = new Desk();
        desk.setDeskName("Test Desk");
        desk.setOffice(office);
        deskService.save(desk);

        user = new User();
        user.setUsername("Tom");
        userService.save(user);
        userService.setCurrentUser(user);
    }

    //tests 'makeABooking()' in BookingRestController
    @Test
    public void whenGivenJsonDetailsThenCreateBooking() {
        String dateString = "2022-01-14";
        Date date = Date.valueOf(dateString);

        //create Map to pass to function
        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("date", dateString);
        //uses desk id of 1, relating to desk saved in 'createTemporaryObjects' function
        bookingDetails.put("deskId", String.valueOf(desk.getDeskId()));

        //call function
        bookingRestController.makeABooking(bookingDetails);

        //get all booking objects, filter based on the values above to see if a Booking object has been saved
        //this should return a single booking Object
        List<Booking> bookingList = bookingService.findAll();
        List bookings = bookingList.stream()
                .filter(booking -> booking.getDate().equals(date))
                .filter(booking -> booking.getDesk().getDeskName().equals("Test Desk"))
                .filter(booking -> booking.getUser().getUsername().equals("Tom"))
                .collect(Collectors.toList());

        //check if a single booking object is contained in 'bookings' to pass test
        assertEquals(1, bookings.size());
    }

    //tests userHasBookingOnDate() in BookingRestController
    @Test
    public void whenCheckingForABookingThenReturnTrueIfExists(){

        //create booking for 15th in database
        Date testDate = Date.valueOf("2022-01-15");
        bookingService.save(new Booking(testDate, user, desk));

        //use user
        assertEquals(true, bookingRestController.userHasBookingOnDate(
                user, Date.valueOf("2022-01-15")));
    }

}

