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
     * Get all users' information of a group by master key of this group or access key of group member
     * @param key
     * @return
     */
    List<UserBean> getGroupListByKey(String key);

    /**
     * Auth a user by his access key.
     * @param accesskey
     * @return
     */
    UserBean authByAccessKey(String accesskey);

    /**
     * Get a user by his user id and group id.
     * @param uid
     * @param gid
     * @return
     */
    UserBean getUserByUserIdInGroup(String uid, String gid);

    /**
     * Update device's device token of a user.
     * @param deviceToken
     * @param uid
     */
    boolean updateDeviceToken(String deviceToken, String uid);

    /**
     *
     * @param receiver
     * @param alertBody
     * @param uid
     * @return
     */
    boolean pushNotificationTo(String receiver, String alertBody, String uid);
}
