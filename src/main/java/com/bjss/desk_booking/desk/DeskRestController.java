package com.bjss.desk_booking.desk;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DeskRestController {

    @Autowired
    DeskService deskService;

    @PostMapping(path = "/admin/viewDesksByOffice")
    public String viewDesks(@RequestBody Map<String, Integer> officeId){
        //create a list of desk transfer objects (by office) to return as json
        System.out.println("OFFICE Id: ----------- " + officeId.get("officeId"));
        List<Desk> deskList = deskService.findAllByOfficeId(officeId.get("officeId"));
        List<DeskDTO> deskDTOList = new ArrayList<>();

        for(Desk d: deskList){
            System.out.println(d.getOffice());
            deskDTOList.add(new DeskDTO(d.getDeskId(),d.getDeskName(),d.getDeskType()
                    ,d.getDeskPosition(),d.getMonitorOption(),d.getDeskStatus(),d.getDeskImageName()));
        }

        return JSONArray.toJSONString(deskDTOList);


    }




}
