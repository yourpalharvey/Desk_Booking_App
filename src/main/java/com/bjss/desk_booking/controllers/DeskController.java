package com.bjss.desk_booking.controllers;

import com.bjss.desk_booking.repository.DeskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
public class DeskController {

    private DeskRepository deskRepo;

    @Autowired
    public DeskController(DeskRepository deskRepo) {
        this.deskRepo = deskRepo;
    }


    //Get desk object by inputting desk name as parameter - e.g. 'Desk 1'
    @RequestMapping(path = "/user/getDeskByName", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value="name", defaultValue = "Desk 8") String name){
        ModelAndView mav = new ModelAndView();
        mav.addObject(deskRepo.getDeskByName(name));

        //This loads the 'home.html' template
        mav.setViewName("home");

        //get desk object from modelMap and print to console
        //
        //this is just to show proof that function is working,
        //can be deleted later once the routes are configured
        Map<String, Object> modelMap = mav.getModelMap();
        System.out.println(modelMap.get("desk").toString());

        return mav;
    }




}
