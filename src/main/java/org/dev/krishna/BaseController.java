package org.dev.krishna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


// http://localhost:8080/krishna/actuator

@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String hello(){
        return "I am listening...";
    }
}
