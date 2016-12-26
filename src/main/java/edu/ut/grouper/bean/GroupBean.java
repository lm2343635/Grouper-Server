package edu.ut.grouper.bean;

import edu.ut.grouper.domain.Group;

import java.util.Date;

public class GroupBean {

    private String gid;
    private String id;
    private String name;
    private int members;
    private Date createDate;
    private String oid;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public GroupBean(Group group) {
        this.gid = group.getGid();
        this.id = group.getId();
        this.name = group.getGname();
        this.members = group.getMembers();
        this.createDate = group.getCreateDate();
        this.oid = (group.getOwner() == null)? null: group.getOwner().getUid();
    }
}
