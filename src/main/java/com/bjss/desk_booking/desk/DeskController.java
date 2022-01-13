package com.bjss.desk_booking.desk;

import com.bjss.desk_booking.office.Office;
import com.bjss.desk_booking.office.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DeskController {

    @Autowired
    private DeskService deskService;

    @Autowired
    private OfficeService officeService;


    /*Adding Desk Method*/
    @RequestMapping(path = "/admin/adddesk") //Mapped HTTP request
    public String addDesk(@ModelAttribute("desk") Desk desk, @RequestParam(value = "image", required = false) MultipartFile file //Request parameter not required and Mapping model name desk to Desk object desk, Expecting a file type from the request

    ) throws IOException, NullPointerException {

        String fileName =null;
        try {
            fileName = StringUtils.cleanPath(file.getOriginalFilename()); //get the actual file name
            desk.setDeskImageName(fileName);
        } catch (Exception e) {
            throw  new NullPointerException("No file name"); //Handle the exception

        }
        if (desk.getDeskName() != null) { //if the desk name is not empty it will save the object
            deskService.save(desk); //Calling deskService save method to save the file
            String uploadDir = "desk/" + desk.getDeskID(); //Setting the local folder name as desk and sub folder according to the desk id
            DeskFileUploadUtil.saveFile(uploadDir, fileName, file); //sending upload dir,filename and the file to the upload utility class
        }
        return "adddesk"; //return HTML file name adddesk
    }
}


