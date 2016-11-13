package edu.ut.grouper.service;

public interface GroupManager {

    /**
     * Register a new group in this server.
     * @param id Group Id
     * @param name Group Name
     * @return
     */
    String registerGroup(String id, String name);
}
