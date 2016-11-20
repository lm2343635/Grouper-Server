package edu.ut.grouper.dao.impl;

import edu.ut.common.hibernate.support.PageHibernateDaoSupport;
import edu.ut.grouper.dao.GroupDao;
import edu.ut.grouper.domain.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupDao")
public class GroupDaoHibernate extends PageHibernateDaoSupport<Group> implements GroupDao {

    public GroupDaoHibernate() {
        super();
        setClass(Group.class);
    }

    public Group getByMasterkey(String masterkey) {
        String hql = "from Group where masterkey = ?";
        List<Group> groups = (List<Group>)getHibernateTemplate().find(hql, masterkey);
        if (groups.size() == 0) {
            return null;
        }
        return groups.get(0);
    }
}
