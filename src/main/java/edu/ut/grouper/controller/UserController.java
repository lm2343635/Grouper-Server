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
    public ResponseEntity addUser(@RequestParam String userId, @RequestParam String name, @RequestParam String email,
                                  @RequestParam String gender, @RequestParam String pictureUrl, @RequestParam boolean owner,
                                  HttpServletRequest request) {
        GroupBean group = groupManager.authByMasterkey(authKey(request));
        if (group == null) {
            return generateBadRequest(ErrorCode.ErrorMasterKey);
        }
        final String accesskey = userManager.addUser(userId, name, email, gender, pictureUrl, group.getId(), owner);
        if (accesskey == null) {
            return generateBadRequest(ErrorCode.ErrorAddUser);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("accesskey", accesskey);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getGroupList(HttpServletRequest request) {
        final List<UserBean> users = userManager.getGroupListByKey(authKey(request));
        if (users == null) {
            return generateBadRequest(ErrorCode.ErrorKeyWrong);
        }
        return generateOK(new HashMap<String, Object>(){{
            put("users", users);
        }});
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public ResponseEntity checkServerState(HttpServletRequest request) {
        final boolean state = userManager.authByAccessKey(authKey(request)) != null;
        return generateOK(new HashMap<String, Object>() {{
            put("ok", state);
        }});
    }

    @RequestMapping(value = "/deviceToken", method = RequestMethod.POST)
    public ResponseEntity submitDeviceToken(@RequestParam String deviceToken, HttpServletRequest request) {
        final UserBean userBean = userManager.authByAccessKey(authKey(request));
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        final boolean success = userManager.updateDeviceToken(deviceToken, userBean.getUid());
        return generateOK(new HashMap<String, Object>() {{
            put("success", success);
        }});
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public ResponseEntity remotePushNotification(@RequestParam String content, @RequestParam String receiver, String category, HttpServletRequest request) {
        UserBean userBean = userManager.authByAccessKey(authKey(request));
        if (userBean == null) {
            return generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        if (!receiver.equals("*")) {
            if (userManager.getUserByUserIdInGroup(receiver, userBean.getGid()) == null) {
                return generateBadRequest(ErrorCode.ErrorPushNoPrivilege);
            }
        }
        final boolean success = userManager.pushNotificationTo(receiver, content, category, userBean.getUid());
        return generateOK(new HashMap<String, Object>() {{
            put("success", success);
        }});
    }

}
