package edu.ut.grouper.dao;

import edu.ut.common.hibernate.support.CrudDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;

import java.util.List;

public interface TransferDao extends CrudDao<Transfer> {

    /**
     * Find tranfers of which receiver is this user
     * @param user
     * @return
     */
    List<Transfer> findByReceiver(User user);

    /**
     * Find transfers which are multicast messages in a group
     * @param group
     * @return
     */
    List<Transfer> findMulticastInGroup(Group group);

}
