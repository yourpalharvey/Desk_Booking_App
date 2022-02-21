package com.bjss.desk_booking;

import com.bjss.desk_booking.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SimpleUnitTests {

    @Test
    void UserDTOTest(){
        UserDTO user = new UserDTO("user");
        assertEquals(user.getUsername(), "user");
    }

}
