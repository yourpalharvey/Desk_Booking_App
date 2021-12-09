package com.bjss.desk_booking.user;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int userId);

    void save(User user);

    void setCurrentUser(User currentUser);

    User getCurrentUser();

    User findByUsername(String username);
}
