package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.Transfer;
import edu.ut.grouper.domain.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
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

    public List<Transfer> findInTids(final List<String> tids) {
        final String hql = "from Transfer where tid in(:tids)";
        return (List<Transfer>) getHibernateTemplate().execute(new HibernateCallback<List<Transfer>>() {
            public List<Transfer> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameterList("tids", tids);
                return query.list();
            }
        });
    }
}
