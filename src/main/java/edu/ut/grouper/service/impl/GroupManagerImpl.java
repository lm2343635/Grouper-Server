package edu.ut.grouper.service.impl;

import edu.ut.grouper.domain.Group;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("groupManager")
public class GroupManagerImpl extends ManagerTemplate implements GroupManager {

    public boolean isGroupExist(String id) {
        return groupDao.getByGroupId(id)!= null;
    }

    public String registerGroup(String id, String name) {
        Group group = new Group();
        group.setGname(name);
        group.setId(id);
        group.setMasterkey(UUID.randomUUID().toString());
        group.setMembers(0);
        group.setCreateDate(new Date());
        if (groupDao.save(group) != null) {
            return group.getMasterkey();
        }
        return null;
    }

    public Group authByMasterkey(String masterkey) {
        return groupDao.getByMasterkey(masterkey);
    }

}
