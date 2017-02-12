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
    public String addUser(String uid, String name, String email, String gender, String pictureUrl, String gid, boolean owner) {
        Group group = groupDao.getByGroupId(gid);
        if (group == null) {
            return null;
        }
        //Find this user in this group.
        User user = userDao.getByUidInGroup(uid, group);
        //If this user is not found, new it.
        if (user == null) {
            user = new User();
            user.setUid(uid);
            user.setName(name);
            user.setEmail(email);
            user.setGender(gender);
            user.setPictureurl(pictureUrl);
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
        } else {
            user.setUid(uid);
            user.setName(name);
            user.setEmail(email);
            user.setGender(gender);
            user.setPictureurl(pictureUrl);
            userDao.update(user);
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
}
