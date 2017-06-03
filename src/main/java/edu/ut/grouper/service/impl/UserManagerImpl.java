package edu.ut.grouper.service.impl;

import edu.ut.grouper.bean.UserBean;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.UserManager;
import edu.ut.grouper.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("userManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    @Transactional
    public String addUser(String node, String gid, boolean owner) {
        Group group = groupDao.getByGroupId(gid);
        if (group == null) {
            return null;
        }
        //Find this user in this group.
        User user = userDao.getByNodeInGroup(node, group);
        //If this user is not found, new it.
        if (user == null) {
            user = new User();
            user.setNode(node);
            user.setAccesskey(owner? group.getMasterkey(): UUID.randomUUID().toString());
            user.setGroup(group);
            if (userDao.save(user) == null) {
                return null;
            }
            if (owner) {
                group.setOwner(user);
            }
            group.setMembers(group.getMembers() + 1);
            groupDao.update(group);
        }
        return user.getAccesskey();
    }

    public List<UserBean> getGroupListByKey(String key) {
        Group group = groupDao.getByMasterkey(key);
        if (group == null) {
            User member = userDao.getByAccesskey(key);
            if (member == null) {
                return null;
            }
            group = member.getGroup();
        }
        List<UserBean> users = new ArrayList<UserBean>();
        for (User user: userDao.findByGroup(group)) {
            users.add(new UserBean(user));
        }
        return users;
    }

    public UserBean authByAccessKey(String accesskey) {
        User user = userDao.getByAccesskey(accesskey);
        if (user == null) {
            return null;
        }
        return new UserBean(user);
    }

    @Transactional
    public boolean updateDeviceToken(String deviceToken, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        user.setDeviceToken(deviceToken);
        userDao.update(user);
        return true;
    }

    public UserBean getUserByNodeInGroup(String node, String gid) {
        Group group = groupDao.get(gid);
        if (group == null) {
            return null;
        }
        User user = userDao.getByNodeInGroup(node, group);
        if (user == null) {
            return null;
        }
        return new UserBean(user);
    }

    public boolean pushNotificationTo(String receiverNode, String alertBody, String category, String uid) {
        User user = userDao.get(uid);
        if (user == null) {
            return false;
        }
        // Push notification to all members except the sender himself if receiver's uid is "*".
        if (receiverNode.equals("*")) {
            for (User reveiver: userDao.findByGroup(user.getGroup())) {
                // Skip sender himself.
                if (reveiver.equals(user)) {
                    continue;
                }
                apnsComponent.push(reveiver.getDeviceToken(), alertBody, category);
            }
        } else {
            User receiver = userDao.getByNodeInGroup(receiverNode, user.getGroup());
            if (receiver == null) {
                return false;
            }
            apnsComponent.push(receiver.getDeviceToken(), alertBody, category);
        }
        return true;
    }

}
