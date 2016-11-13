package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.UserDao;
import edu.ut.grouper.domain.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoHibernate extends PageHibernateDaoSupport<User> implements UserDao {
    public UserDaoHibernate() {
        super();
        setClass(User.class);
    }
}
