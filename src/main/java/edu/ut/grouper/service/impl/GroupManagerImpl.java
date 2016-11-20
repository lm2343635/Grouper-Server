package edu.ut.grouper.service.impl;

import edu.ut.grouper.domain.Group;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RemoteProxy(name = "GroupManager")
@Service("groupManager")
public class GroupManagerImpl extends ManagerTemplate implements GroupManager {

    @RemoteMethod
    public String registerGroup(String id, String name) {
        Group group = new Group();
        group.setGname(name);
        group.setId(id);
        group.setMasterkey(UUID.randomUUID().toString());
        group.setMembers(0);
        if (groupDao.save(group) != null) {
            return group.getMasterkey();
        }
        return null;
    }

    public Group authByMasterkey(String masterkey) {
        return groupDao.getByMasterkey(masterkey);
    }
}
