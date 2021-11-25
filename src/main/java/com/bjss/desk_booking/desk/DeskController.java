package com.bjss.desk_booking.desk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DeskController {

    @Autowired
    private DeskService deskService;

    @RequestMapping(path = "/admin/adddesk")
    public String addDesk(@ModelAttribute("desk") Desk desk, @RequestParam(value = "image",required = false) MultipartFile file) throws IOException,NullPointerException


    {



        String fileName = "";
        try {
            fileName = StringUtils.cleanPath(file.getOriginalFilename()); //get the acrual file name
            desk.setDeskImageName(fileName);


        } catch (Exception e) {

        }

        if (desk.getDeskName() != null) {
            desk.setBooked(false);
            deskService.save(desk);



            String uploadDir = "desk/" + desk.getDeskID();

            DeskFileUploadUtil.saveFile(uploadDir, fileName, file); //sending upload dir,filename and the file to the upload utility
        }


        return "adddesk";


    }

}


