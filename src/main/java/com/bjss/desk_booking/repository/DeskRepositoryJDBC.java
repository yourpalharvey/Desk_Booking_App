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
        Desk desk = (Desk) this.jdbcTemplate.queryForObject(
                "select id, name, hasStanding, hasWindow, hasDualMonitor from desks where name=?",
                new DeskMapper(),
                new Object[]{name}
        );

        return desk;
    }
}
