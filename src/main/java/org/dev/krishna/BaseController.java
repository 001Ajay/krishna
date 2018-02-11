package org.dev.krishna;


import org.dev.krishna.service.fileutils.FileInfo;
import org.dev.krishna.service.fileutils.FileUtilsService;
import org.dev.krishna.service.fileutils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


// http://localhost:8080/krishna/actuator

@Controller
@RequestMapping("/")
public class BaseController {

    @Autowired
    FileUtilsService fileUtilsService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String hello(){
        return "I am listening...";
    }

    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    public @ResponseBody List<FileInfo> scan(@PathVariable String path){
        String location = "C:\\Users\\Ajay\\.m2\\repository\\org\\projectlombok\\lombok\\1.16.20";
        return fileUtilsService.scanDirectory(location);
    }


    /**
     * This webservices searches for duplicate file inside a specific location.
     * It compares extension, file size and then file content using byte stream
     * (byte comparison is optimised to compare using bytes progressively instead
     * of comparing the entire file content allowing comparison of big files easily)
     *
     * @return List of Pair of duplicate file information
     * @throws Exception
     */
    @RequestMapping(value = "/checkDuplicates", method = RequestMethod.GET)
    public @ResponseBody List<Pair> checkCopy()throws Exception{
        String location = "C:\\Users\\Ajay\\.m2\\repository\\org\\projectlombok\\lombok\\1.16.20";
        return fileUtilsService.checkDuplicatesInFolder(location);
    }

}
