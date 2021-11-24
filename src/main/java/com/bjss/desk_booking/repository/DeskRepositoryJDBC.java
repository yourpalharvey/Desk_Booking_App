package com.bjss.desk_booking.repository;

import com.bjss.desk_booking.DTO.CreateDeskDTO;
import com.bjss.desk_booking.DTO.Desk;
import com.bjss.desk_booking.model.DeskMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

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

    @Override
    public void addDesk(CreateDeskDTO createDesk){
        String name = createDesk.getName();
        boolean hasStanding = createDesk.hasStanding();
        boolean hasWindow = createDesk.hasWindow();
        boolean hasDualMonitor = createDesk.hasDualMonitor();

        System.out.println(name + hasStanding + hasWindow + hasDualMonitor);

        String insertSql = "INSERT INTO desks (name, hasStanding, hasWindow, hasDualMonitor) " +
                "VALUES (?, ?, ?, ?)";

        Object[] params = new Object[]{name, hasStanding, hasWindow, hasDualMonitor};
        int[] types = new int[]{Types.VARCHAR, Types.TINYINT, Types.TINYINT, Types.TINYINT};

        try{
            int row = jdbcTemplate.update(insertSql, params, types);
            System.out.println(row + " rows inserted.");
        } catch (Exception e) {
            System.out.println("NO ROWS INSERTED");
            //todo - do something when this error is thrown
        }
    }

    //TODO - CREATE A METHOD FOR THE ADMIN TO REMOVE DESKS FROM DATABASE

}
