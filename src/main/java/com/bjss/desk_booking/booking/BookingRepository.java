package com.bjss.desk_booking.booking;

import java.sql.Date;

public interface BookingRepository {
    
    public Object getBookingByDeskAndDate(int deskId, Date date);
    public boolean bookingExists(int deskId, Date date);

}
