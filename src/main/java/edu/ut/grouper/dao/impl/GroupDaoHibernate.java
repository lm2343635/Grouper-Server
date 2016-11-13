package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.GroupDao;
import edu.ut.grouper.domain.Group;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoHibernate extends PageHibernateDaoSupport<Group> implements GroupDao {
    public GroupDaoHibernate() {
        super();
        setClass(Group.class);
    }
}
