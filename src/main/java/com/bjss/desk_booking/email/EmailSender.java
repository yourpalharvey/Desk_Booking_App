package com.bjss.desk_booking.email;

import com.bjss.desk_booking.booking.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailSender {

    @Autowired
    JavaMailSender mailSender;

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

    public void confirmEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            System.out.println("CONFIRM BOOKING EMAIL SENDER");

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Booking Confirmed");
            message.setText("Congratulation for your  booking(booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate()+"\nThis is a "+booking.getDesk().getDeskType()+"\nThe Desk ID is: "+booking.getDesk().getDeskId()
                    +" The location of the Desk is: "+booking.getDesk().getDeskPosition()+"\nIt has "+booking.getDesk().getMonitorOption()+" monitors");
            mailSender.send(message);

        }

    }


    public void pendingEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Booking Pending");
            message.setText("We are sorry to inform you that your booking needs admin approval due to high demand.You will get booking information within three working days. Booking(booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate()+"\nThis is a "+booking.getDesk().getDeskType()+"\nThe Desk ID is: "+booking.getDesk().getDeskId()
                    +" The location of the Desk is: "+booking.getDesk().getDeskPosition()+"\nIt has "+booking.getDesk().getMonitorOption()+" monitors");
            mailSender.send(message);

        }

    }

    public void checkinEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Check in Confirmation");
            message.setText("You have checked in for the booking(booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate());
            mailSender.send(message);
        }
    }


    public void cancelledEmailSender(Booking booking) throws MessagingException
    {
        if(booking.getUser().getUserEmail()!=null) {

            String from = "deskbookingt05@gmail.com";
            String to = booking.getUser().getUserEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            //Sending Booking confirmation
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Booking canceled!");
            message.setText("You have canceled the booking(booking ID:"+booking.getBookingId()+"\nBooking Date: "+booking.getDate());
            mailSender.send(message);
        }
    }
}
