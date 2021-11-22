package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
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
    //update the userbooking table
    @Override
    public void createBooking(Booking booking) {
        Date date = booking.getDate();
        int deskId = booking.getDeskId();

        System.out.println(date);
        System.out.println(deskId);

        //Create sql statement to insert to database
        Object[] params = new Object[]{deskId, date};
        int[] types = new int[]{Types.INTEGER, Types.TIMESTAMP};

        String insertSql = "INSERT INTO bookings " +
                "(desk_id, date) " +
                "VALUES (?, ?)";


        int row = jdbcTemplate.update(insertSql, params, types);
        System.out.println(row + " rows inserted.");

    }

    @Override
    public void getBookingId(Booking booking){

    }
}
