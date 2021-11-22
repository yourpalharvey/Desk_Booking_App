package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.Desk;
import com.bjss.desk_booking.model.DeskMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeskRepositoryJDBC implements DeskRepository {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public DeskRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Desk getDeskByName(String name) {
        //ADD CODE HERE TO RETURN BOOKING OBJECT FROM DESK ID (TODAYS DATE)? FROM DESK ID (USE JDBC VID TO CHECK THIS)
        Desk desk = (Desk) this.jdbcTemplate.queryForObject(
                "select id, name, hasStanding, hasWindow, hasDualMonitor from desk where name=?",
                new DeskMapper(),
                new Object[]{name}
        );

        return desk;
    }
}
