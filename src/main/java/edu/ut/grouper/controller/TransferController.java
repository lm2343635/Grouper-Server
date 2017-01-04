package edu.ut.grouper.controller;

import edu.ut.grouper.controller.util.ResponseTool;
import edu.ut.grouper.bean.TransferBean;
import edu.ut.grouper.bean.UserBean;
import edu.ut.grouper.controller.util.ErrorCode;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public ResponseEntity send(@RequestParam String share, @RequestParam String receiver, @RequestParam String mid, HttpServletRequest request) {
        String key = request.getHeader("key");
        TransferManager.PutResult result = transferManager.putShare(key, share, receiver, mid);
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

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity getShareContent(@RequestParam final List<String> id, HttpServletRequest request) {
        String key = request.getHeader("key");
        UserBean user = userManager.authByAccessKey(key);
        if (user == null) {
            return ResponseTool.generateBadRequest(ErrorCode.ErrorAccessKey);
        }

        final List<Map> contents = new ArrayList<Map>();
        for (String tid: id) {

            Map<String, Object> content = new HashMap<String, Object>();
            content.put("id", tid);
            TransferBean transfer = transferManager.getShareContent(tid);
            if (transfer == null) {
                content.put("result", "notFound");
                content.put("data", null);
            } else if (transfer.getReceiver() != null && !transfer.getReceiver().equals(user.getId())) {
                content.put("result", "noPrivilege");
                content.put("data", null);
            } else {
                content.put("result", "found");
                if (transfer.getReceiver() == null) {
                    transfer.setReceiver("*");
                }
                content.put("data", transfer);
            }

            contents.add(content);
        }

        return ResponseTool.generateOK(new HashMap<String, Object>() {{
            put("contents", contents);
        }});
    }

}