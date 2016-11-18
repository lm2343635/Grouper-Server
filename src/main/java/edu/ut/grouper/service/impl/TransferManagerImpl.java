package edu.ut.grouper.service.impl;

import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service("transferManager")
public class TransferManagerImpl extends ManagerTemplate implements TransferManager {

    public int saveShare(String sid, String content, String object) {
        Transfer transfer = new Transfer();
        transfer.setSid(sid);
        transfer.setContent(content);
        transfer.setObject(object);
        transfer.setSavetime(new Date());
        transfer.setCount(0);
        String tid = transferDao.save(transfer);
        return 0;
    }

}
