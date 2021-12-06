//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bjss.desk_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
    public loginController() {
    }

    @RequestMapping({"/login"})
    public String login(String name) {
        return "login";
    }
}

