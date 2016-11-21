package edu.ut.grouper.controller;

import edu.ut.common.util.ResponseTool;
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
        if (groupManager.isGroupExist(id)) {
            return ResponseTool.generateBadRequest(1001, "Group id is exist in this server.");
        }
        final String masterkey = groupManager.registerGroup(id, name);
        if (masterkey == null) {
            return ResponseTool.generateBadRequest(1002, "Register group error.");
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("masterkey", masterkey);
        }});
    }

}
