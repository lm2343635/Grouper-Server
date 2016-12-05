package edu.ut.grouper.controller;

import edu.ut.grouper.service.TransferManager;
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
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferManager transferManager;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity send(@RequestParam String sid, @RequestParam String content, @RequestParam String object) {
        int count = transferManager.saveShare(sid, content, object);
        Map<String, Object> data = new HashMap();
        data.put("count", count);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @RequestMapping(value = "receive", method = RequestMethod.POST)
    public ResponseEntity receive(@RequestParam String sid) {

        return new ResponseEntity("1678", HttpStatus.OK);

    }
}
