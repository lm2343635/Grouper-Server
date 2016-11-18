package edu.ut.grouper.service.impl;

import edu.ut.grouper.domain.Group;
import edu.ut.grouper.domain.User;
import edu.ut.grouper.service.UserManager;
import edu.ut.grouper.service.util.ManagerTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userManager")
public class UserManagerImpl extends ManagerTemplate implements UserManager {

    public String addUser(String uid, String name, String email, String gender, String pictureUrl, Group group, boolean owner) {
        User user = new User();
        user.setUid(uid);
        user.setName(name);
        user.setEmail(email);
        user.setGender(gender);
        user.setPictureurl(pictureUrl);
        user.setAccesskey(UUID.randomUUID().toString());
        user.setGroup(group);
        if (userDao.save(user) != null) {
            if (owner) {
                group.setOwner(user);
                groupDao.update(group);
            }
            return user.getAccesskey();
        }
        return null;
    }
}
