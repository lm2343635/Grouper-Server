package edu.ut.grouper.controller;

import edu.ut.grouper.bean.GroupBean;
import edu.ut.grouper.bean.UserBean;
import edu.ut.grouper.controller.common.ControllerTemplate;
import edu.ut.grouper.controller.common.ErrorCode;
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
public class UserController extends ControllerTemplate {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam String uid, @RequestParam String name, @RequestParam String email,
                                  @RequestParam String gender, @RequestParam String pictureUrl, @RequestParam boolean owner,
                                  HttpServletRequest request) {
        String key = request.getHeader("key");
        GroupBean group = groupManager.authByMasterkey(key);
        if (group == null) {
            return generateBadRequest(ErrorCode.ErrorMasterKey);
        }
        final String accesskey = userManager.addUser(uid, name, email, gender, pictureUrl, group.getId(), owner);
        if (accesskey == null) {
            return generateBadRequest(ErrorCode.ErrorAddUser);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("accesskey", accesskey);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getGroupList(HttpServletRequest request) {
        String key = request.getHeader("key");
        final List<UserBean> users = userManager.getGroupListByKey(key);
        if (users == null) {
            return generateBadRequest(ErrorCode.ErrorKeyWrong);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("users", users);
        }});
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public ResponseEntity checkServerState(HttpServletRequest request) {
        String key = request.getHeader("key");
        final boolean state = userManager.authByAccessKey(key) != null;
        return generateOK(new HashMap<String, Object>() {{
            put("ok", state);
        }});
    }

    @RequestMapping(value = "/deviceToken", method = RequestMethod.POST)
    public ResponseEntity submitDeviceToken(@RequestParam String deviceToken, HttpServletRequest request) {
        String key = request.getHeader("key");
        final UserBean userBean = userManager.authByAccessKey(key);
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        final boolean success = userManager.updateDeviceToken(deviceToken, userBean.getUuid());
        return generateOK(new HashMap<String, Object>() {{
            put("success", true);
        }});
    }

}
