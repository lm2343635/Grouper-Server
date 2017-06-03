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
import java.util.Map;

@Service("transferManager")
public class TransferManagerImpl extends ManagerTemplate implements TransferManager {

    @Transactional
    public PutResult putShare(String accesskey, String share, String receiverNode, String messageId) {
        User sender = userDao.getByAccesskey(accesskey);
        if (sender == null) {
            return PutResult.AccessKeyWrong;
        }
        User receiver = null;
        if (!receiverNode.equals("*") && receiverNode != null) {
            receiver = userDao.getByNodeInGroup(receiverNode, sender.getGroup());
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
        transfer.setMessageId(messageId);
        if (transferDao.save(transfer) == null) {
            return PutResult.InternelError;
        }
        return PutResult.Success;
    }

    @Transactional
    public PutResult reputShare(String accesskey, Map<String, String> shares, String receiverNode) {
        User sender = userDao.getByAccesskey(accesskey);
        if (sender == null) {
            return PutResult.AccessKeyWrong;
        }
        User receiver = null;
        if (!receiverNode.equals("*") && receiverNode != null) {
            receiver = userDao.getByNodeInGroup(receiverNode, sender.getGroup());
            if (receiver == null) {
                return PutResult.NoReceiverFound;
            }
            if (receiver == sender) {
                return PutResult.SendSelfForbidden;
            }
        }
        for (String messageId : shares.keySet()) {
            // Check is transfer object is existed or not by messageId.
            Transfer transfer = transferDao.getByMessageId(messageId);
            if (transfer != null) {
                // Update share content if transfer object is existed.
                transfer.setShare(shares.get(messageId));
                // Update savetime.
                transfer.setSavetime(System.currentTimeMillis() / 1000L);
                transferDao.update(transfer);
            } else {
                // Create a new transfer object if it not existed.
                transfer = new Transfer();
                transfer.setShare(shares.get(messageId));
                transfer.setReceiver(receiver);
                transfer.setSender(sender);
                transfer.setSavetime(System.currentTimeMillis() / 1000L);
                transfer.setMessageId(messageId);
                transferDao.save(transfer);
            }
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
        for (Transfer transfer : transferDao.findByReceiver(user)) {
            ids.add(transfer.getTid());
        }
        //Find tranfers for all (multicast).
        for (Transfer transfer : transferDao.findMulticastInGroup(user.getGroup())) {
            //Multicast message for this user himself cannot be added.
            if (transfer.getSender().getUid().equals(user.getUid())) {
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
        for (Transfer transfer : transferDao.findInTids(tids)) {
            transfers.add(new TransferBean(transfer));
        }
        return transfers;
    }

    public List<String> notExsitedMessageIds(List<String> messageIds) {
        List<String> existedMessagesIds = new ArrayList<String>();
        for (Transfer transfer : transferDao.findInMessageIds(messageIds)) {
            existedMessagesIds.add(transfer.getMessageId());
        }
        messageIds.removeAll(existedMessagesIds);
        return messageIds;
    }

}
