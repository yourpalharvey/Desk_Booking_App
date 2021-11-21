package com.bjss.desk_booking.model;

import com.bjss.desk_booking.DTO.Desk;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeskMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        System.out.println(rs.getInt("id"));
        System.out.println(rs.getString("name"));
        System.out.println(rs.getBoolean("hasStanding"));
        return new Desk(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBoolean("hasStanding"),
                rs.getBoolean("hasWindow"),
                rs.getBoolean("hasDualMonitor"));

    }

}