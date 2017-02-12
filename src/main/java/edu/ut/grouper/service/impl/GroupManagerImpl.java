package edu.ut.grouper.service.impl;

import edu.ut.grouper.bean.GroupBean;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.GroupManager;
import edu.ut.grouper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("groupManager")
public class GroupManagerImpl extends ManagerTemplate implements GroupManager {

    public boolean isGroupExist(String id) {
        return groupDao.getByGroupId(id)!= null;
    }

    @Transactional
    public String registerGroup(String id, String name) {
        Group group = new Group();
        group.setGname(name);
        group.setId(id);
        group.setMasterkey(UUID.randomUUID().toString());
        group.setMembers(0);
        group.setCreateDate(System.currentTimeMillis() / 1000L);
        if (groupDao.save(group) != null) {
            return group.getMasterkey();
        }
        return null;
    }

    public GroupBean authByMasterkey(String masterkey) {
        Group group = groupDao.getByMasterkey(masterkey);
        if (group == null) {
            return null;
        }
        return new GroupBean(group);
    }

    public GroupBean authByKey(String key) {
        Group group = groupDao.getByMasterkey(key);
        if (group != null) {
            return new GroupBean(group);
        }
        User user = userDao.getByAccesskey(key);
        if (user == null) {
            return null;
        }
        return new GroupBean(user.getGroup());
    }

    @Transactional
    public boolean initializeGroup(String gid, int servers, int threshold) {
        Group group = groupDao.get(gid);
        if (group == null) {
            return false;
        }
        if (group.getServers() != null) {
            return false;
        }
        group.setServers(servers);
        group.setThreshold(threshold);
        groupDao.update(group);
        return true;
    }

}
