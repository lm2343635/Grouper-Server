package edu.ut.grouper.controller;

import edu.ut.common.util.ResponseTool;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private GroupManager groupManager;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam String uid, @RequestParam String name, @RequestParam String email,
                                  @RequestParam String gender, @RequestParam String pictureUrl, @RequestParam boolean owner,
                                  HttpServletRequest request) {
        String key = request.getHeader("key");
        Group group = groupManager.authByMasterkey(key);
        if (group == null) {
            return ResponseTool.generateBadRequest(2001, "Master key is wrong.");
        }
        final String accesskey = userManager.addUser(uid, name, email, gender, pictureUrl, group, owner);
        if (accesskey == null) {
            return ResponseTool.generateBadRequest(2002, "Add user internel error.");
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("accesskey", accesskey);
        }});
    }

    @ResponseBody
    @RequestMapping(value = "/group_list", method = RequestMethod.GET)
    public ResponseEntity getGroupList(HttpServletRequest request) {
        String key = request.getHeader("key");
        final List<User> users = userManager.getGroupListByAccesskey(key);
        for (User user: users) {
            System.out.println(user.getName());
        }
        if (users == null) {
            return ResponseTool.generateBadRequest(2003, "Access key is wrong.");
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("users", users);
        }});
    }
}
