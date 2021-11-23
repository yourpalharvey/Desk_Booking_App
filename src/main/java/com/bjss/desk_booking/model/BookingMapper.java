package com.bjss.desk_booking.model;

import com.bjss.desk_booking.DTO.Booking;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("desk_id"),
                rs.getDate("date"));

    }

}