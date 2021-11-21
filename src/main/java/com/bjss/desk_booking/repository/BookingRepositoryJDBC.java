package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.Booking;

import com.bjss.desk_booking.DTO.Desk;
import com.bjss.desk_booking.model.DeskMapper;
import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;

@Repository
public class BookingRepositoryJDBC implements BookingRepository{

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public BookingRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Desk getDeskById(String name) {
        //ADD CODE HERE TO RETURN BOOKING OBJECT FROM DESK ID (TODAYS DATE)? FROM DESK ID (USE JDBC VID TO CHECK THIS)
        System.out.println(name);
        Desk desk = (Desk) this.jdbcTemplate.queryForObject(
                "select id, name, hasStanding, hasWindow, hasDualMonitor from desk where name=?",
                new DeskMapper(),
                new Object[]{name}
        );

        System.out.println(desk.getName());
        System.out.println(desk.hasStanding());
        return desk;
    }
}
