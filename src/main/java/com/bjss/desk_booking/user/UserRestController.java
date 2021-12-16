package com.bjss.desk_booking.user;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/public/getAllUsers")
    public String getAllUsers() {
        //get list of all user's usernames only and return as json
        List<User> userList = userService.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user: userList){
            userDTOList.add(new UserDTO(user.getUsername()));
        }

        String jsonString = JSONArray.toJSONString(userDTOList);
        return jsonString;
    }

}
