package edu.ut.grouper.service.common;

import edu.ut.grouper.component.APNsComponent;
import edu.ut.grouper.dao.GroupDao;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class ManagerTemplate {

    @Autowired
    protected TransferDao transferDao;

    @Autowired
    protected GroupDao groupDao;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected APNsComponent apnsComponent;

    public TransferDao getTransferDao() {
        return transferDao;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
