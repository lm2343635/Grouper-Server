package edu.ut.grouper.service;

import edu.ut.grouper.bean.UserBean;

import java.util.List;

public interface UserManager {

    /**
     * Add a user in a group, if this user is exist, update his user info.
     *
     * @param gid
     * @param owner
     * @return The access key of this user for this group
     */
    public String addUser(String node, String gid, boolean owner);

    /**
     * Get all users' information of a group by master key of this group or access key of group member
     *
     * @param key
     * @return
     */
    List<UserBean> getGroupListByKey(String key);

    /**
     * Auth a user by his access key.
     *
     * @param accesskey
     * @return
     */
    UserBean authByAccessKey(String accesskey);

    /**
     * Get a user by his node identifier and group id.
     *
     * @param node
     * @param gid
     * @return
     */
    UserBean getUserByNodeInGroup(String node, String gid);

    /**
     * Update device's device token of a user.
     *
     * @param deviceToken
     * @param uid
     */
    boolean updateDeviceToken(String deviceToken, String uid);

    /**
     * @param receiver
     * @param alertBody
     * @param category
     * @param uid
     * @return
     */
    boolean pushNotificationTo(String receiver, String alertBody, String category, String uid);
}
