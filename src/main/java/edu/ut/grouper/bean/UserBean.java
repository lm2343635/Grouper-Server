package edu.ut.grouper.bean;

import edu.ut.grouper.domain.User;

public class UserBean {

    private String uid;
    private String node;
    private String gid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public UserBean(User user) {
        this.uid = user.getUid();
        this.node = user.getNode();
        this.gid = user.getGroup().getGid();
    }
}
