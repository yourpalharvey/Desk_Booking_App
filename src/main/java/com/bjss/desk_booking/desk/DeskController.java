package com.bjss.desk_booking.desk;

import com.bjss.desk_booking.office.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DeskController {

    @Autowired
    private DeskService deskService;

    @Autowired
    private OfficeService officeService;



    /*Adding Desk Method*/
    @RequestMapping(path = "/admin/adddesk")
    public String addDesk(@ModelAttribute("desk") Desk desk, @RequestParam(value = "image",required = false) MultipartFile file
//                          @RequestParam String deskName,
//                          @RequestParam int officeId
//                          @RequestParam String desktype,
//                          @RequestParam String deskPosition,
//                          @RequestParam int monitorOption
                          ) throws IOException,NullPointerException {

        String fileName = "";
        try {
            fileName = StringUtils.cleanPath(file.getOriginalFilename()); //get the acrual file name
            desk.setDeskImageName(fileName);


        } catch (Exception e) {

        }

        if (desk.getDeskName() != null) {

            deskService.save(desk);
            System.out.println("done");



            String uploadDir = "desk/" + desk.getDeskId();

            DeskFileUploadUtil.saveFile(uploadDir, fileName, file); //sending upload dir,filename and the file to the upload utility
        }


        return "adddesk";


    }


    /*Show Desk*/







}


