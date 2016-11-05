package edu.ut.grouper.controller;

import edu.ut.grouper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/text")
    public String index() {
        return "text.jsp";
    }

    @RequestMapping("/json")
    @ResponseBody
    public List<String> json(){
        return userService.getAllUsernames();
    }
}