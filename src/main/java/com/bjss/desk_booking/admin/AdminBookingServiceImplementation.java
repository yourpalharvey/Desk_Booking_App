package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.DTO.Booking;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminBookingServiceImplementation implements AdminBookingService {

    private AdminBookingRepo adminBookingRepo;

    @Autowired
    public AdminBookingServiceImplementation(AdminBookingRepo theAdminBookingRepo) {
        adminBookingRepo=theAdminBookingRepo;

    }

    @Override
    public List<Booking> findAll() {
        return (List<Booking>) adminBookingRepo.findAll();
    }



    @Override
    public Booking findById(int id) {
        Optional<Booking> result = adminBookingRepo.findById(id);

        Booking thebooking = null;

        if (result.isPresent()) {
            thebooking = result.get();
        } else {

            throw new RuntimeException("Did not find project - " + id);
        }

        return thebooking;
    }

    @Override
    public void save(Booking thebooking) {
        adminBookingRepo.save(thebooking);
    }

    @Override
    public void deleteById(int id) {
        adminBookingRepo.deleteById(id);
    }

}
