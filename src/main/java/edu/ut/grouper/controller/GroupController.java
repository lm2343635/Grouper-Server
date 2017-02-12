package edu.ut.grouper.controller;

import edu.ut.grouper.bean.UserBean;
import edu.ut.grouper.bean.GroupBean;
import edu.ut.grouper.controller.common.ControllerTemplate;
import edu.ut.grouper.controller.common.ErrorCode;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.UserManager;
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
public class GroupController extends ControllerTemplate {


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerGroup(@RequestParam String id, @RequestParam String name) {
        if (groupManager.isGroupExist(id)) {
            return generateBadRequest(ErrorCode.ErrorGroupExsit);
        }
        final String masterkey = groupManager.registerGroup(id, name);
        if (masterkey == null) {
            return generateBadRequest(ErrorCode.ErrorGroupRegister);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("masterkey", masterkey);
        }});
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getGroupInformation(HttpServletRequest request) {
        String key = request.getHeader("key");
        final GroupBean group = groupManager.authByKey(key);
        if (group == null) {
            return generateBadRequest(ErrorCode.ErrorKeyWrong);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("group", group);
        }});
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public ResponseEntity restoreServer(String uid, String accesskey) {
        final GroupBean group = groupManager.authByKey(accesskey);
        if (group == null) {
            return generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        final UserBean user = userManager.authByAccessKey(accesskey);
        if (!user.getId().equals(uid)) {
            return generateBadRequest(ErrorCode.ErrorUserNotInGroup);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("owner", group.getOid().equals(user.getId()));
            put("group", group);
        }});
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ResponseEntity initializeGroup(@RequestParam int servers, @RequestParam int threshold, HttpServletRequest request) {
        String key = request.getHeader("key");
        GroupBean group = groupManager.authByMasterkey(key);
        if (group == null) {
            return generateBadRequest(ErrorCode.ErrorMasterKey);
        }
        if (!groupManager.initializeGroup(group.getGid(), servers, threshold)) {
            return generateBadRequest(ErrorCode.ErrorGroupInitialized);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("success", true);
        }});
    }

}
