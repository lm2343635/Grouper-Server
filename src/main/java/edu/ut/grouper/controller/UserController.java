package edu.ut.grouper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam String uid, @RequestParam String name, @RequestParam String email,
                                  @RequestParam String gender, @RequestParam String pictureUrl, @RequestParam boolean owner) {

        return null;
    }
}
