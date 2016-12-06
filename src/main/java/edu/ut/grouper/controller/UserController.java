package edu.ut.grouper.controller;

import edu.ut.grouper.bean.GroupBean;
import edu.ut.grouper.bean.UserBean;
import edu.ut.common.util.ResponseTool;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
        GroupBean group = groupManager.authByMasterkey(key);
        if (group == null) {
            return ResponseTool.generateBadRequest(2001, "Master key is wrong.");
        }
        final String accesskey = userManager.addUser(uid, name, email, gender, pictureUrl, group.getId(), owner);
        if (accesskey == null) {
            return ResponseTool.generateBadRequest(2004, "Add user internel error.");
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("accesskey", accesskey);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getGroupList(HttpServletRequest request) {
        String key = request.getHeader("key");
        final List<UserBean> users = userManager.getGroupListByKey(key);
        if (users == null) {
            return ResponseTool.generateBadRequest(2003, "Master key or access key is wrong.");
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("users", users);
        }});
    }
}
