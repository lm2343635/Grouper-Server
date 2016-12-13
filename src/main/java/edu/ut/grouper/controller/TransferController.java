package edu.ut.grouper.controller;

import edu.ut.common.util.ResponseTool;
import edu.ut.grouper.bean.TransferBean;
import edu.ut.grouper.bean.UserBean;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public ResponseEntity send(@RequestParam String share, @RequestParam String receiver, HttpServletRequest request) {
        String key = request.getHeader("key");
        TransferManager.PutResult result = transferManager.putShare(key, share, receiver);
        if (result == TransferManager.PutResult.AccessKeyWrong) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        if (result == TransferManager.PutResult.NoReceiverFound) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorNoReceiverFound);
        }
        if (result == TransferManager.PutResult.SendSelfForbidden) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorSendSelfForbidden);
        }
        if (result == TransferManager.PutResult.InternelError) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorPutShare);
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("success", true);
        }});
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getList(HttpServletRequest request) {
        String key = request.getHeader("key");
        final List<String> ids = transferManager.listShare(key);
        if (ids == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        return ResponseTool.generateOK(new HashMap<String, Object>(){{
            put("shares", ids);
        }});
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getShareContent(String id, HttpServletRequest request) {
        String key = request.getHeader("key");
        UserBean user = userManager.authByAccessKey(key);
        if (user == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorAccessKey);
        }
        final TransferBean transfer = transferManager.getShareContent(id);
        if (transfer == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorNoShareFound);
        }
        if (!transfer.getReceiver().equals(user.getId())) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorShareNoPrivilege);
        }
        return ResponseTool.generateOK(new HashMap<String, Object>() {{
            put("share", transfer);
        }});
    }

}