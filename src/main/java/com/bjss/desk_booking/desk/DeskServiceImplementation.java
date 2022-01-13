package com.bjss.desk_booking.desk;

import com.bjss.desk_booking.booking.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


    /*JPA=Java Persistence API*/

@Service //Defining it is a service class of spring boot application
public class DeskServiceImplementation implements DeskService {

    @Autowired //injecting DeskRepo class for object mapping from/into database
    private DeskRepo deskRepo;



    /*Method for finding all desk from database*/
    @Override
    public List<Desk> findAll()
    {
        return deskRepo.findAll();
    }
    /*Method for finding desk using OfficeId from database*/
    @Override
    public List<Desk> findAllByOfficeId(int officeId)
    {
        System.out.println(officeId);
        List<Desk> officeDeskList = deskRepo.findAll()
                .stream()
                .filter(desk -> desk.getOffice().getOfficeId() == officeId)
                .collect(Collectors.toList());
        return officeDeskList;
    }


    /*Finding one desk by its deskId from database using JPA */
    @Override
    public Desk findById(int id)
    {
        Optional<Desk> result = deskRepo.findById(id);

        Desk theDesk = null;

        if (result.isPresent()) {
            theDesk = result.get();
        } else {

            throw new RuntimeException("Did not find desk - " + id);
        }

        return theDesk;
    }

    /*Saving desk object in the database*/
    @Override
    public void save(Desk theDesk)
    {
        deskRepo.save(theDesk);
    }

    /*Deleting desk from database by deskId*/
    @Override
    public void deleteById(int id)
    {
        deskRepo.deleteById(id);
    }


}
