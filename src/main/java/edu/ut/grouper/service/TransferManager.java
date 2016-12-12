package edu.ut.grouper.service;

import org.springframework.transaction.annotation.Transactional;

public interface TransferManager {

    public enum PutResult {
        AccessKeyWrong,
        NoReceiverFound,
        InternelError,
        Success
    }

    /**
     *
     * @param accesskey
     * @param share
     * @param receiverUid
     * @return
     */
    PutResult putShare(String accesskey, String share, String receiverUid);
}
