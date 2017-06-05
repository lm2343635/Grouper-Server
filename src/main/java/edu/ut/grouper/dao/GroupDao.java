package edu.ut.grouper.dao;

import edu.ut.common.hibernate.support.CrudDao;
import edu.ut.grouper.domain.Group;

public interface GroupDao extends CrudDao<Group> {

    /**
     * Get a group by master key
     *
     * @param masterkey
     * @return
     */
    Group getByMasterkey(String masterkey);

    /**
     * Get a group by id
     *
     * @param id
     * @return
     */
    Group getByGroupId(String id);

}
