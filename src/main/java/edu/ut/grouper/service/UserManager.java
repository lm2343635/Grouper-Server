package edu.ut.grouper.service;

import edu.ut.grouper.bean.UserBean;

import java.util.List;

public interface UserManager {

    /**
     * Add a user in a group, if this user is exist, update his user info.
     * @param uid
     * @param name
     * @param email
     * @param gender
     * @param pictureUrl
     * @param gid
     * @param owner
     * @return The access key of this user for this group
     */
    String addUser(String uid, String name, String email, String gender, String pictureUrl, String gid, boolean owner);

    /**
     *
     * @param accesskey
     * @return
     */
    List<UserBean> getGroupListByAccesskey(String accesskey);
}
