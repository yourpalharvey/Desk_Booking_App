package com.bjss.desk_booking.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepo;

    @Autowired
    public UserServiceImplementation(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    //if a booking with the selected id is found in the db, return Booking object.
    //else, return null
    @Override
    public User findById(int userId) {
        Optional<User> result = userRepo.findById(userId);

        User user = null;

        if(result.isPresent()){
            user = result.get();
        } else {
            throw new RuntimeException("Did not find booking with ID: " + userId);
        }

        return user;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public void deleteById(int userId) {
        userRepo.deleteById(userId);
    }


}
