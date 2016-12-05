package edu.ut.grouper.service.impl;

import edu.ut.bean.UserBean;
import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.UserManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("userManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    public String addUser(String uid, String name, String email, String gender, String pictureUrl, Group group, boolean owner) {
        System.out.println(owner);
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
            user.setAccesskey(UUID.randomUUID().toString());
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

    public List<UserBean> getGroupListByAccesskey(String accesskey) {
        User member = userDao.getByAccesskey(accesskey);
        if (member == null) {
            return null;
        }
        List<UserBean> users = new ArrayList<UserBean>();
        for (User user: userDao.findByGroup(member.getGroup())) {
            users.add(new UserBean(user));
        }
        return users;
    }
}
