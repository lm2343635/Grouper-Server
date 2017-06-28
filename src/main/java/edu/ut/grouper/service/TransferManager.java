package edu.ut.grouper.service;

import edu.ut.grouper.bean.TransferBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface TransferManager {

    /**
     * Put a share into transfer table.
     *
     * @param accesskey
     * @param shares
     * @return
     */
    int putShares(String accesskey, String shares);

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

    /**
     *
     * @param messageIds
     * @return
     */
    List<String> notExsitedMessageIds(List<String> messageIds);

}
