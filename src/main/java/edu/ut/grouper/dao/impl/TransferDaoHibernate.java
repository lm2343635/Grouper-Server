package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Transfer;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("transferDao")
public class TransferDaoHibernate extends PageHibernateDaoSupport<Transfer> implements TransferDao {

    public TransferDaoHibernate() {
        super();
        setClass(Transfer.class);
    }

}
