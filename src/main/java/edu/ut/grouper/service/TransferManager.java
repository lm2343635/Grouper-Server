package edu.ut.grouper.service;

import edu.ut.grouper.bean.TransferBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
     * @param receiver
     * @param messageId
     * @return
     */
    PutResult putShare(String accesskey, String share, String receiver, String messageId);

    /**
     * Reput shares into transfer table.
     *
     * @param accesskey
     * @param shares    Shares is a map, it contains messageId and share content like {"messageId": "shareContent"}
     * @param receiver
     * @return
     */
    PutResult reputShare(String accesskey, Map<String, String> shares, String receiver);

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
