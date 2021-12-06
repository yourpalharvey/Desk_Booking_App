package com.bjss.desk_booking.user;

import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/userLogin")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody String username, String password) {
        User user = userService.getUser(username,password);
        System.out.println("user = " + user);
        return "hello";
//        return userService.getUser(username,password);
    }

}
