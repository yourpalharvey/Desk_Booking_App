package com.bjss.desk_booking.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // returns a list of all the bookings that have the same office ID
    @Override
    public List<Booking> findAllByOfficeId(int officeId) {
        List<Booking> officeBookingList = bookingRepo.findAll()
                .stream()
                .filter(booking -> booking.getDesk().getOffice().getOfficeId() == officeId)
                .collect(Collectors.toList());
        return officeBookingList;
    }

    // returns a list of all the booking that have the same desk ID (same desk)
    public List<Booking> findAllByDeskId(int deskId){
        List<Booking> deskBookingList = bookingRepo.findAll()
                .stream()
                .filter(booking -> booking.getDeskId() == deskId)
                .collect(Collectors.toList());
        return deskBookingList;
    }

    // returns the booking from the booking ID in the booking table
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

    // Returns a list of all the bookings that share the same User ID
    public List<Booking> findByUserId(int userId){
        List<Booking> userBookings = new ArrayList<>();
        List<Booking> allBookings = bookingRepo.findAll();

        // If the booking in the table has the user ID entered, add it to the new list
        for(Booking b: allBookings){
            if(b.getUser().getUserId() == userId){
                userBookings.add(b);
            }
        }

        // print statement for debugging. Can be deleted.
        for(Booking b : userBookings){
            System.out.println(b);
        }

        return userBookings;

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
