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
    //when this method is called, another must also be called to
    //      update the userbookings table
    @Override
    public void createBooking(CreateBookingDTO createBooking) {
        Date date = createBooking.getDate();
        int deskId = createBooking.getDeskId();

        System.out.println(date);
        System.out.println(deskId);

        //todo - will need to add a check here to stop duplicate inserts - can use .bookingExists() to check this

        Object[] params = new Object[]{deskId, date};
        int[] types = new int[]{Types.INTEGER, Types.TIMESTAMP};

        String insertSql = "INSERT INTO bookings " +
                "(desk_id, date) " +
                "VALUES (?, ?)";


        int row = jdbcTemplate.update(insertSql, params, types);
        System.out.println(row + " rows inserted.");

    }

    @Override
    public Booking getBookingByDeskAndDate(int deskId, Date date){
        Booking booking = (Booking) this.jdbcTemplate.queryForObject(
                "select id, desk_id, date from bookings where desk_id=? and date=?",
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
