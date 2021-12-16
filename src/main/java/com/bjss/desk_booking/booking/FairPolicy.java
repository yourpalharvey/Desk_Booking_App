package com.bjss.desk_booking.booking;

import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FairPolicy {

    @Autowired
    BookingService bookingService;

    @Autowired
    DeskService deskService;

    public boolean fairPolicy(Booking booked)
    {
        List<Booking> bookingList=bookingService.findAll();
        int count=0;
        for (Booking booking:bookingList)
        {
            if(booking.getDate().equals(booked.getDate()))
            {
                count++; //find out number of booking on that day
            }
        }

        // get the total number of desks for the office where the booking has been made
        int desksize=deskService.findAllByOfficeId(booked.getDesk().getOffice().getOfficeId()).size();
        float bookedPercentage=((float)count/desksize); //find the percentage of booked desk on that day
        bookedPercentage=bookedPercentage*100;
        java.util.Date date = new java.util.Date();

        //Will reduce the complexcity

        if(bookedPercentage>=70)   //if more than 70% desk is booked it will check for user rating

        {
            for (Booking booking : bookingList) {
                if (booking.getUser().getUserId() == booked.getUser().getUserId() && booking.getDate().before(date) || booking.getDate().equals(date)) {
                    if (!booking.isChecked()) { //check if user has any previous missed booking
                        booking.setChecked(true); //
                        booking.getUser().ratingDecrement(); //call for penalty function in the user rating
                        int rating = booking.getUser().getRating();
                        if (rating <= 70) {  //if rating is less than 70 then it will return false and need admin approval
                            return false;
                        } else {
                            return true;   //otherwise book the desk automatically
                        }
                    } else {
                        if (booking.getUser().getRating() <= 70) { //if the user has no previous missed booking still it will check for rating
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
            if(booked.getUser().getRating()<70)
            {
                if(!booked.isChecked())
                {
                    booked.getUser().ratingDecrement();
                    booked.setChecked(true);
                }
                return false;
            }
            else
            {
                return true;
            }
        }
        return true;
    }
}
