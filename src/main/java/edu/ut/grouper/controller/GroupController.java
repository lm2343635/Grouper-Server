package edu.ut.grouper.controller;

import edu.ut.grouper.service.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupManager groupManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerGroup(@RequestParam String id, @RequestParam String name) {
        String masterKey = groupManager.registerGroup(id, name);
        Map<String, Object> data = new HashMap();
        if (masterKey != null) {
            data.put("masterKey", masterKey);
            return new ResponseEntity(data, HttpStatus.OK);
        } else {
            data.put("errCode", 1001);
            data.put("errMsg", "Register group error.");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        }
    }

}
