package com.bjss.desk_booking.user;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int userId);

    void save(User user);
//
//    int isRight(String username, String password);

    public User getUser(String username,String password);
}
