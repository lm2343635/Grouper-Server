package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("transferDao")
public class TransferDaoHibernate extends PageHibernateDaoSupport<Transfer> implements TransferDao {

    public TransferDaoHibernate() {
        super();
        setClass(Transfer.class);
    }

    public List<Transfer> findByReceiver(User user) {
        if (user == null) {
            return null;
        }
        String hql = "from Transfer where receiver = ?";
        return (List<Transfer>) getHibernateTemplate().find(hql, user);
    }

    public List<Transfer> findMulticastInGroup(Group group) {
        String hql = "from Transfer where sender.group = ? and receiver = null";
        return (List<Transfer>) getHibernateTemplate().find(hql, group);
    }
}
