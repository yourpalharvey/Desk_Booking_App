package com.bjss.desk_booking.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService{

    private BookingRepository bookingRepo;

    @Autowired
    public BookingServiceImplementation(BookingRepository bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    //if a booking with the selected id is found in the db, return Booking object.
    //else, return null
    //todo - add some error handling/error message for when this happens

    @Override
    public Booking findById(int bookingId) {
        Optional<Booking> result = bookingRepo.findById(bookingId);

        Booking booking = null;

        if(result.isPresent()){
            booking = result.get();
        } else {
            throw new RuntimeException("Did not find booking with ID: " + bookingId);
        }

        return booking;
    }

    @Override
    public void save(Booking booking) {
        bookingRepo.save(booking);
    }

    @Override
    public void deleteById(int bookingId) {
        bookingRepo.deleteById(bookingId);
    }
}
