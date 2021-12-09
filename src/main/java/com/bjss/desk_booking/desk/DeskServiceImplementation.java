package com.bjss.desk_booking.desk;
import com.bjss.desk_booking.booking.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeskServiceImplementation implements DeskService {

    @Autowired
    private DeskRepo deskRepo;


    @Override
    public List<Desk> findAll() {
        return deskRepo.findAll();
    }
    @Override
    public List<Desk> findAllByOfficeId(int officeId){
        System.out.println(officeId);
        List<Desk> officeDeskList = deskRepo.findAll()
                .stream()
                .filter(desk -> desk.getOffice().getOfficeId() == officeId)
                .collect(Collectors.toList());
        //System.out.println(officeBookingList);
        return officeDeskList;
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
