package edu.ut.grouper.service;

import edu.ut.grouper.domain.Group;

public interface GroupManager {

    /**
     * Register a new group in this server.
     * @param id Group Id
     * @param name Group Name
     * @return
     */
    String registerGroup(String id, String name);

    /**
     * Find a group by its master key
     * @param masterkey
     * @return
     */
    Group authByMasterkey(String masterkey);
}
