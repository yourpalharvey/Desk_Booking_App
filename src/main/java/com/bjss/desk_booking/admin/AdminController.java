package com.bjss.desk_booking.admin;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.booking.BookingService;
import com.bjss.desk_booking.desk.Desk;
import com.bjss.desk_booking.desk.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class AdminController {


    @Autowired
    DeskService deskService;

    @Autowired
    BookingService bookingService;

    @Autowired
    private JavaMailSender mailSender;


    @RequestMapping(path = "/admindashboard")
    public String adminDashboard(Model model)
    {

        List<Booking> bookingList=bookingService.findAll();
        model.addAttribute("bookingList", bookingList);

        return "adminPanel";
    }


/*Controller for showing all the previous booking*/
    @RequestMapping(path = "/previousbooking")
    public String previousBooking(Model model) {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println();

        List<Booking> allbookingList=bookingService.findAll();
        List<Booking> bookingList=new ArrayList<>();
        for (Booking b:allbookingList)
        {
            if(b.getDate().after(date) || b.getDate().equals(date))
            {bookingList.add(b);

            }

        }


        model.addAttribute("bookingList",bookingList);

        return "previousbooking";

    }

    @GetMapping("/previousbooking/{id}")
    public String deleteBooking(@PathVariable("id") int id, Model model) {
        bookingService.deleteById(id);


        return "redirect:/previousbooking";
    }



    @GetMapping("/admindeskstatus/{id}")
    public String deleteDesk(@PathVariable("id") int id, Model model) {
       deskService.deleteById(id);


        return "redirect:/admindeskstatus";
    }

    @GetMapping(value = "/admindeskstatus")
    public String deskstatus(Model model)

    {
        List<Desk> deskList=deskService.findAll();
        model.addAttribute("deskList", deskList);
        return "deskStatus";



    }

    @GetMapping(value = "/admingetBookingsByDate")
    public String bookingByDate(Model model,@RequestParam (required = false)java.sql.Date date)

    {
        if(date==null)
        {
            return "previousbooking";
        }


        List<Booking> allbookingList=bookingService.findAll();
        List<Booking> bookingList=new ArrayList<>();
        for (Booking b:allbookingList)
        {
            if(b.getDate().equals(date))  //Search by Date
            {bookingList.add(b);

            }

        }

        model.addAttribute("bookingList",bookingList);
        return "adminPanelDated";



    }

    @GetMapping(value = "/admingetBookingsByUserName")
    public String bookingByUser(Model model,@RequestParam (required = false)java.sql.Date date,String name)

    {




        List<Booking> allbookingList=bookingService.findAll();
        List<Booking> bookingList=new ArrayList<>();
        for (Booking b:allbookingList)
        {
            if(b.getUser().getUsername().equals(name))  //Search by name
            {bookingList.add(b);

            }

        }

        model.addAttribute("bookingList",bookingList);
        return "adminPanelDated";



    }


    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") int id, Model model) {

        Booking booking=bookingService.findById(id);
        try {
            cancelEmailSender(booking);
        }
        catch (Exception E)
        {

        }

        bookingService.deleteById(id);





        return "redirect:/pending";
    }

    @GetMapping("/accept/{id}")
    public String acceptBooking(@PathVariable("id") int id, Model model) {
        Booking booking=bookingService.findById(id);
        booking.setApproved(true);
        try {
            confirmationEmailSender(booking);
        }
        catch (Exception e)
        {

        }

        bookingService.save(booking);

        return "redirect:/pending";
    }

    @RequestMapping("/pending")
    public String pendingBooking(Model model)
    {

        List<Booking> allbookingList=bookingService.findAll();
        List<Booking> bookingList=new ArrayList<>();
        Date date = new Date();
        for (Booking b:allbookingList)
        {
            if(!b.isApproved() && b.getDate().after(date) ||b.getDate().equals(date))  //If the booking requires booking
            {
                bookingList.add(b);

            }

        }

        model.addAttribute("bookingList",bookingList);
        return "PendingBooking";

    }




    public void cancelEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Desk Booking information");
            message.setText("We are very sorry to say that your booking request (booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate()+"\nhas been canceled.Sorry for the inconvenience");
            mailSender.send(message);

        }

    }



    public void confirmationEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Desk Booking Confirmation");
            message.setText("Your booking(booking ID:"+booking.getBookingId()+"\tBooking Date: "+booking.getDate()+" is confirmed \nThis is a "+booking.getDesk().getDeskType()+"\nThe Desk ID is: "+booking.getDesk().getDeskId()
                    +"The location of the Desk is: "+booking.getDesk().getDeskPosition()+"\nIt has "+booking.getDesk().getMonitorOption()+" monitors");
            mailSender.send(message);

        }

    }


}
