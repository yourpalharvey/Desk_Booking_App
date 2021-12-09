package com.bjss.desk_booking.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepo;

    private User currentUser;

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    //gets the currently logged-in user
    public User getCurrentUser(){
        return currentUser;
    }

    @Autowired
    public UserServiceImplementation(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(int userId) {
        Optional<User> result = userRepo.findById(userId);

        User user = null;

        if(result.isPresent()){
            user = result.get();
        } else {
            throw new RuntimeException("Did not find user with ID: " + userId);
        }

        return user;
    }

    @Override
    public User findByUsername(String username){
        return userRepo.findAll().stream().filter(a -> a.getUsername().equals(username)).collect(Collectors.toList()).get(0);
    }


    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
