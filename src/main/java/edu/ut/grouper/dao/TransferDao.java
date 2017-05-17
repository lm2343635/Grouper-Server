package edu.ut.grouper.dao;

import edu.ut.common.hibernate.support.CrudDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;

import java.util.List;

public interface TransferDao extends CrudDao<Transfer> {

    /**
     * Find tranfers of which receiver is this user
     *
     * @param user
     * @return
     */
    List<Transfer> findByReceiver(User user);

    /**
     * Find transfers which are multicast messages in a group
     *
     * @param group
     * @return
     */
    List<Transfer> findMulticastInGroup(Group group);

    /**
     * Find tranfers in a set of ids
     *
     * @param tids
     * @return
     */
    List<Transfer> findInTids(List<String> tids);

    /**
     *
     * @param messageIds
     * @return
     */
    List<Transfer> findInMessageIds(List<String> messageIds);

    /**
     * Find transfers before a past save time
     *
     * @param savetime
     * @return
     */
    List<Transfer> findBeforeSaveTime(Long savetime);

    /**
     * Get a transfer by messageId.
     *
     * @param messageId
     * @return
     */
    Transfer getByMessageId(String messageId);

}
