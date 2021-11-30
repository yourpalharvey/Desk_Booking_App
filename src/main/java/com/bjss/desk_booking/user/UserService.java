package com.bjss.desk_booking.user;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(int userId);

    public void save(User user);

}
