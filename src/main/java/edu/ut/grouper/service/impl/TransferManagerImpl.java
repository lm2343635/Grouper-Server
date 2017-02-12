package edu.ut.grouper.service.impl;

import edu.ut.grouper.bean.TransferBean;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("transferManager")
public class TransferManagerImpl extends ManagerTemplate implements TransferManager {

    @Transactional
    public PutResult putShare(String accesskey, String share, String receiverUid, String mid) {
        User sender = userDao.getByAccesskey(accesskey);
        if (sender == null) {
            return PutResult.AccessKeyWrong;
        }
        User receiver = null;
        if (!receiverUid.equals("") && receiverUid != null) {
            receiver = userDao.getByUidInGroup(receiverUid, sender.getGroup());
            if (receiver == null) {
                return PutResult.NoReceiverFound;
            }
            if (receiver == sender) {
                return PutResult.SendSelfForbidden;
            }
        }
        Transfer transfer = new Transfer();
        transfer.setShare(share);
        transfer.setReceiver(receiver);
        transfer.setSender(sender);
        transfer.setSavetime(System.currentTimeMillis() / 1000L);
        transfer.setMid(mid);
        if (transferDao.save(transfer) == null) {
            return PutResult.InternelError;
        }
        return PutResult.Success;
    }

    public List<String> listShare(String accesskey) {
        User user = userDao.getByAccesskey(accesskey);
        if (user == null) {
            return null;
        }
        List<String> ids = new ArrayList<String>();
        //Find transfers for this user.
        for (Transfer transfer: transferDao.findByReceiver(user)) {
            ids.add(transfer.getTid());
        }
        //Find tranfers for all (multicast).
        for (Transfer transfer: transferDao.findMulticastInGroup(user.getGroup())) {
            //Multicast message for this user himself cannot be added.
            if (transfer.getSender().getUuid().equals(user.getUuid())) {
                continue;
            }
            ids.add(transfer.getTid());
        }
        return ids;
    }

    public TransferBean getShareContent(String tid) {
        Transfer transfer = transferDao.get(tid);
        if (transfer == null) {
            return null;
        }
        return new TransferBean(transfer);
    }

    public List<TransferBean> getSharesContent(List<String> tids) {
        List<TransferBean> transfers = new ArrayList<TransferBean>();
        for (Transfer transfer: transferDao.findInTids(tids)) {
            transfers.add(new TransferBean(transfer));
        }
        return transfers;
    }
}
