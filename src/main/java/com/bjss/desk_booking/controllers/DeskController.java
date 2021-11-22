package com.bjss.desk_booking.controllers;

import com.bjss.desk_booking.repository.DeskRepository;
import com.bjss.desk_booking.repository.DeskRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
public class DeskController {

    private DeskRepository deskRepo;

    @Autowired
    public DeskController(DeskRepository deskRepo) {
        this.deskRepo = deskRepo;
    }



    @RequestMapping(path = "/user/getDesk", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value="name", defaultValue = "Desk 8") String name){
        ModelAndView mav = new ModelAndView();
        mav.addObject(deskRepo.getDeskByName(name));

        //This loads the 'home.html' template to display the details
        mav.setViewName("home");

        //get desk object from modelMap and print to console
        Map<String, Object> modelMap = mav.getModelMap();
        System.out.println(modelMap.get("desk").toString());

        return mav;
    }




}
