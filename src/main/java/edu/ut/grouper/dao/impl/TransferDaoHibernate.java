package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Transfer;

public class TransferDaoHibernate extends PageHibernateDaoSupport<Transfer> implements TransferDao {

    public TransferDaoHibernate() {
        super();
        setClass(Transfer.class);
    }

}
