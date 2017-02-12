package edu.ut.grouper.service.common;

import edu.ut.grouper.dao.GroupDao;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.dao.UserDao;

import javax.annotation.Resource;

public class ManagerTemplate {

    @Resource(name = "transferDao")
    protected TransferDao transferDao;
    @Resource(name = "groupDao")
    protected GroupDao groupDao;
    @Resource(name = "userDao")
    protected UserDao userDao;

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
