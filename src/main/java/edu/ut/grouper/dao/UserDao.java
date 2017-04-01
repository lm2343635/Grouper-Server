package edu.ut.grouper.dao;

import edu.ut.common.hibernate.support.CrudDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {

    /**
     * Find an user by his userId and group
     * @param uid
     * @param group
     * @return
     */
    User getByUserIdInGroup(String uid, Group group);

    /**
     * Find an user by his access key
     * @param accesskey
     * @return
     */
    User getByAccesskey(String accesskey);

    /**
     * Find all users in a group.
     * @param group
     * @return
     */
    List<User> findByGroup(Group group);

}
