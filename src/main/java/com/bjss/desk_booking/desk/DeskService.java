package com.bjss.desk_booking.desk;

import java.util.List;

public interface DeskService {
    public List<Desk> findAll();

    public Desk findById(int deskId);

    public List<Desk> findAllByOfficeId(int officeId);

    public void save(Desk desk);

    public void deleteById(int deskId);
}
