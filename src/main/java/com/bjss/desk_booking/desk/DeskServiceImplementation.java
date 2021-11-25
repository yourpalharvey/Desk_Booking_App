package com.bjss.desk_booking.desk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeskServiceImplementation implements DeskService {
    private DeskRepo deskRepo;

    @Autowired
    public DeskServiceImplementation(DeskRepo theDeskRepo) {
        deskRepo=theDeskRepo;

    }

    @Override
    public List<Desk> findAll() {
        return (List<Desk>) deskRepo.findAll();
    }



    @Override
    public Desk findById(int id) {
        Optional<Desk> result = deskRepo.findById(id);

        Desk   thedesk = null;

        if (result.isPresent()) {
            thedesk = result.get();
        }
        else {

            throw new RuntimeException("Did not find project - " + id);
        }

        return thedesk ;
    }





    @Override
    public void save(Desk theDesk) {
        deskRepo.save(theDesk);
    }

    @Override
    public void deleteById(int id) {
        deskRepo.deleteById(id);
    }


}
