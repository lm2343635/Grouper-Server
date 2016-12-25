package edu.ut.grouper.controller;

import edu.ut.grouper.controller.util.ResponseTool;
import edu.ut.grouper.bean.GroupBean;
import edu.ut.grouper.controller.util.ErrorCode;
import edu.ut.grouper.service.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupManager groupManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerGroup(@RequestParam String id, @RequestParam String name) {
        if (groupManager.isGroupExist(id)) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorGroupExsit);
        }
        final String masterkey = groupManager.registerGroup(id, name);
        if (masterkey == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorGroupRegister);
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("masterkey", masterkey);
        }});
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ResponseEntity getGroupInformation(HttpServletRequest request) {
        String key = request.getHeader("key");
        final GroupBean group = groupManager.authByKey(key);
        if (group == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorKeyWrong);
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("group", group);
        }});
    }
}
