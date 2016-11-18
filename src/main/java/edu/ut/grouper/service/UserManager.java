package edu.ut.grouper.service;

import edu.ut.grouper.domain.Group;

public interface UserManager {

    /**
     * Add a user in a group
     * @param uid
     * @param name
     * @param email
     * @param gender
     * @param pictureUrl
     * @param group
     * @param owner
     * @return
     */
    String addUser(String uid, String name, String email, String gender, String pictureUrl, Group group, boolean owner);
}
