package edu.ut.grouper.service.impl;

import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service("transferManager")
public class TransferManagerImpl extends ManagerTemplate implements TransferManager {

    public PutResult putShare(String accesskey, String share, String receiverUid) {
        User sender = userDao.getByAccesskey(accesskey);
        if (sender == null) {
            return PutResult.AccessKeyWrong;
        }
        User receiver = null;
        if (!receiverUid.equals("") && receiverUid != null) {
            userDao.getByUidInGroup(receiverUid, sender.getGroup());
            if (receiver == null) {
                return PutResult.NoReceiverFound;
            }
        }
        Transfer transfer = new Transfer();
        transfer.setShare(share);
        transfer.setReceiver(receiver);
        transfer.setSender(sender);
        transfer.setSavetime(new Date());
        if (transferDao.save(transfer) == null) {
            return PutResult.InternelError;
        }
        return PutResult.Success;
    }
}
