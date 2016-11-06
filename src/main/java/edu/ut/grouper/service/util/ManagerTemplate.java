package edu.ut.grouper.service.util;

import edu.ut.grouper.dao.TransferDao;

import javax.annotation.Resource;

public class ManagerTemplate {

    @Resource(name = "transferDao")
    protected TransferDao transferDao;

    public TransferDao getTransferDao() {
        return transferDao;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
}
