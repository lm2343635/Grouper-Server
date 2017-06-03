package edu.ut.grouper.service;

import edu.ut.grouper.bean.GroupBean;

public interface GroupManager {

    /**
     * Is group id exist in database.
     *
     * @param id
     * @return
     */
    boolean isGroupExist(String id);

    /**
     * Register a new group in this server.
     *
     * @param id   Group Id
     * @param name Group Name
     * @return
     */
    String registerGroup(String id, String name);

    /**
     * Find a group by its master key
     *
     * @param masterkey
     * @return
     */
    GroupBean authByMasterkey(String masterkey);

    /**
     * Find a group by its master key
     *
     * @param key
     * @return
     */
    GroupBean authByKey(String key);

    /**
     * Initialize a group by submitting server count, threshold and delection interval.
     *
     * @param gid
     * @param servers
     * @param threshold
     * @param interval
     * @return
     */
    boolean initializeGroup(String gid, int servers, int threshold, int interval);

}
