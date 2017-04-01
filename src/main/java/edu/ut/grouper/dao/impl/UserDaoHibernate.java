package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.UserDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoHibernate extends PageHibernateDaoSupport<User> implements UserDao {

    public UserDaoHibernate() {
        super();
        setClass(User.class);
    }

    public User getByUserIdInGroup(String uid, Group group) {
        String hql = "from User where uid = ? and group =?";
        List<User> users = (List<User>)getHibernateTemplate().find(hql, uid, group);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public User getByAccesskey(String accesskey) {
        String hql = "from User where accesskey = ?";
        List<User> users = (List<User>)getHibernateTemplate().find(hql, accesskey);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public List<User> findByGroup(Group group) {
        String hql = "from User where group = ?";
        return (List<User>) getHibernateTemplate().find(hql, group);
    }

}
