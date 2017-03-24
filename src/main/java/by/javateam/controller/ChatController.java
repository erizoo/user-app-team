package by.javateam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String startPage(){
        return "chat";
    }

}
