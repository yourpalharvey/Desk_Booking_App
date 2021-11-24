package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.DTO.CreateBookingDTO;
import com.bjss.desk_booking.model.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.Types;

@Repository
public class BookingRepositoryJDBC implements BookingRepository{

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //creates a record in booking table

    @Override
    public void createBooking(CreateBookingDTO createBooking) {
        Date date = createBooking.getDate();
        int deskId = createBooking.getDeskId();
        //int userId = createBooking.getUserId();

        System.out.println(date);
        System.out.println(deskId);
        //System.out.println(userId);

        Object[] params = new Object[]{date, deskId};
        int[] types = new int[]{Types.TIMESTAMP, Types.INTEGER};

        //todo: currently hard-coded to user_id = 1 (update this later)
        String insertSql = "INSERT INTO booking " +
                "(date, desk_id, user_id) " +
                "VALUES (?, ?, 1)";

        //If this desk does not exist in desks table - throw an error
//        try{
            int row = jdbcTemplate.update(insertSql, params, types);
            System.out.println(row + " rows inserted.");
//        } catch (Exception e) {
//            System.out.println("NO ROWS INSERTED - DOES THIS DESK EXIST");
 //           //todo - do something when this error is thrown
  //      }
    }

    @Override
    public Booking getBookingByDeskAndDate(int deskId, Date date){
        Booking booking = (Booking) this.jdbcTemplate.queryForObject(
                "select booking_id, desk_id, date from booking where desk_id=? and date=?",
                new BookingMapper(),
                new Object[]{deskId, date}
        );
        return booking;
    }

    //use this method to check if booking is in database already
    public boolean bookingExists(int deskId, Date date){
        Booking toCheck = getBookingByDeskAndDate(deskId, date);

        //check if booking already exists with the same desk id and date
        if(deskId == toCheck.getDeskId()
                && date.compareTo(toCheck.getDate()) == 0){
            System.out.println("BOOKING EXISTS ALREADY");
            return true;
        }
        System.out.println("BOOKING DOES NOT EXIST");
        return false;

    }


}
