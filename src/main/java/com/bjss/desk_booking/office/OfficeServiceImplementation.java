package com.bjss.desk_booking.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImplementation implements OfficeService {

    private OfficeRepository officeRepo;

    @Autowired
    public OfficeServiceImplementation(OfficeRepository officeRepo){
        this.officeRepo = officeRepo;
    }

    @Override
    public List<Office> findAll() {
        return officeRepo.findAll();
    }

    //if office with the selected id is found in the db, return office object.
    //else, return null
    @Override
    public Office findById(int officeId) {
        Optional<Office> result = officeRepo.findById(officeId);

        Office office = null;

        if(result.isPresent()){
            office = result.get();
        } else {
            throw new RuntimeException("Did not find office with ID: " + officeId);
        }

        return office;
    }

    @Override
    public void save(Office office) {
        officeRepo.save(office);
    }

    @Override
    public Office findByName(String officeName) {
        return null;
    }


}
