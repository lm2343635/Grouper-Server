package edu.ut.grouper.service;

import edu.ut.grouper.bean.TransferBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransferManager {

    public enum PutResult {
        AccessKeyWrong,
        NoReceiverFound,
        SendSelfForbidden,
        InternelError,
        Success
    }

    /**
     * Put a share into transfer table.
     *
     * @param accesskey
     * @param share
     * @param receiverUserId
     * @param messageId
     * @return
     */
    PutResult putShare(String accesskey, String share, String receiverUserId, String messageId);

    /**
     * Get a share list by access key, only get share for this user or all users.
     *
     * @param accesskey
     * @return
     */
    List<String> listShare(String accesskey);

    /**
     * Get content of a share by tid
     *
     * @param tid
     * @return
     */
    TransferBean getShareContent(String tid);

    /**
     * Get content of shares by tids.
     *
     * @param tids
     * @return
     */
    List<TransferBean> getSharesContent(List<String> tids);
}
