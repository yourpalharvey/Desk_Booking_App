package com.bjss.desk_booking;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import com.bjss.desk_booking.office.Office;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class DeskTest {

    private static Desk desk;


    @BeforeAll
    public static void before() {
        desk=new Desk();
        desk.setDeskName("Desk201");
        desk.setDeskPosition("Window");
        desk.setDeskImageName("JPG1");



    }

    @Test
    public void testGetDeskName() {
        assertEquals("Desk201", desk.getDeskName());
    }

    @Test
    public void testGetDeskPosition() {
        assertEquals("Window", desk.getDeskPosition());
    }

    @Test
    public void testGetDeskImageName() {
        assertEquals("JPG1", desk.getDeskImageName());
    }

}
