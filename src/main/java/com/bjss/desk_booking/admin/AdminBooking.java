package com.bjss.desk_booking.admin;


import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.Date;

@Controller
public class AdminBooking {

    @Autowired
    private DeskService deskService;

    @Autowired
    private AdminBookingService adminBookingService;

    @Autowired
    private AdminBookingRepo adminBookingRepo;


    /*Booking from Admin Panel*/
    @RequestMapping (value = "/admin/adminbooking")
    public String adminbooking(Model model, @RequestParam(required = false) String startdate, String enddate) throws ParseException {

        //         SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        //         String d="2021-11-27";
        //         Date date=format.parse(d);

        String dateString = "2021-11-27";
        Date date= Date.valueOf(dateString);

        Booking book=adminBookingService.findById(1);
        book.setStartdate(date);

             adminBookingService.save(book);

             List <Booking> bookedList=adminBookingService.findAll();
             List <Booking> freeList =new ArrayList<>();
             for(int i=0;i<bookedList.size();i++)
             {

             }

        try {
            Date startDate = Date.valueOf(dateString); //format.parse(startdate);
            Date endtDate = Date.valueOf(dateString); //format.parse(enddate);

            for(int i=0;i<bookedList.size();i++)
            {
                if(bookedList.get(i).getStartdate().before(startDate)&&bookedList.get(i).getStartdate().after(endtDate))
                {
                    freeList.add(bookedList.get(i));
                    for (int j=0;j<freeList.size();j++)
                    {
                        System.out.println(freeList.get(j));
                    }
                }
            }


        }
        catch ( Exception e)
        {

        }

        List<Desk> deskList=deskService.findAll();

        model.addAttribute("deskList", deskList);
        return "Adminbooking";

    }
}
