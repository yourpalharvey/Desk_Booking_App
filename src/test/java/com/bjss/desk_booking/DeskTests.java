package com.bjss.desk_booking;

import com.bjss.desk_booking.desk.*;
import com.bjss.desk_booking.office.Office;
import com.bjss.desk_booking.office.OfficeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class DeskTests {

    @Autowired
    private DeskService deskService;

    @Autowired
    private OfficeService officeService;

    private Office office;
    private Desk desk1,desk2;

    @BeforeAll
    public void before(){
        office = new Office();
        officeService.save(office);

        desk1 = new Desk();
        desk2 = new Desk();

        desk1.setOffice(office);
        desk2.setOffice(office);

        deskService.save(desk1);
        deskService.save(desk2);
    }

    //tests findAllByOfficeId() in DeskServiceImplementation
    @Test
    public void whenGivenOfficeIdThenReturnAllRelatedDesks() {
        //check whether the length of the arraylist returned is the
        // same length as the number of desks inserted with given id
        assertEquals(2, deskService.findAllByOfficeId(office.getOfficeId()).size());
    }
}
