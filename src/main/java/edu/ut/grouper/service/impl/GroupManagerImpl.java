package edu.ut.grouper.service.impl;

import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

@RemoteProxy(name = "GroupManager")
@Service("groupManager")
public class GroupManagerImpl extends ManagerTemplate implements GroupManager {

    @RemoteMethod
    public String registerGroup(String id, String name) {
        return null;
    }
}
