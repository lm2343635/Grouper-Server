package edu.ut.grouper.service;

import org.springframework.transaction.annotation.Transactional;

public interface TransferManager {

    /**
     * Save a share from mobile devices
     * @param sid
     * @param content
     * @param object
     * @return
     */
    int saveShare(String sid, String content, String object);
}
