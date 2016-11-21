package edu.ut.grouper.dao;

import edu.ut.common.hibernate.support.CrudDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;

public interface UserDao extends CrudDao<User> {

    /**
     * Find an user by its uid and group
     * @param uid
     * @param group
     * @return
     */
    User getByUidInGroup(String uid, Group group);

}
