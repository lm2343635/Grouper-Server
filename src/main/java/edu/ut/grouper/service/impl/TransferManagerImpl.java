package edu.ut.grouper.service.impl;

import edu.ut.grouper.bean.TransferBean;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.TransferManager;
import edu.ut.grouper.service.common.ManagerTemplate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("transferManager")
public class TransferManagerImpl extends ManagerTemplate implements TransferManager {

    @Transactional
    public int putShares(String accesskey, String shares) {
        User sender = userDao.getByAccesskey(accesskey);
        if (sender == null) {
            return -1;
        }
        int success = 0;
        JSONArray shareArray = JSONArray.fromObject(shares);
        for (int i = 0; i < shareArray.size(); i++) {
            JSONObject shareObject = shareArray.getJSONObject(i);
            String share = shareObject.getString("share");
            String messageId = shareObject.getString("messageId");
            String receiverNode = shareObject.getString("receiver");

            User receiver = null;
            if (!receiverNode.equals("*") && receiverNode != null) {
                receiver = userDao.getByNodeInGroup(receiverNode, sender.getGroup());
                // If receiver cannot be found or receiver is same with sender, skip this share.
                if (receiver == null || receiver == sender) {
                    continue;
                }
            }

            Transfer transfer = transferDao.getByMessageId(messageId);
            if (transfer != null) {
                // Update share content if transfer object is existed.
                transfer.setShare(share);
                // Update savetime.
                transfer.setSavetime(System.currentTimeMillis() / 1000L);
                transferDao.update(transfer);
            } else {
                // Create a new transfer object if it not existed.
                transfer = new Transfer();
                transfer.setShare(share);
                transfer.setReceiver(receiver);
                transfer.setSender(sender);
                transfer.setSavetime(System.currentTimeMillis() / 1000L);
                transfer.setMessageId(messageId);
                transferDao.save(transfer);
            }
            success++;
        }
        return success;
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
