package edu.ut.grouper.bean;

import edu.ut.grouper.domain.Group;

public class GroupBean {

    private String gid;
    private String id;
    private String name;
    private int members;
    private long createDate;
    private String oid;
    private int servers;
    private int threshold;

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getServers() {
        return servers;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public GroupBean(Group group) {
        this.gid = group.getGid();
        this.id = group.getId();
        this.name = group.getGname();
        this.members = group.getMembers();
        this.createDate = group.getCreateDate();
        this.oid = (group.getOwner() == null)? null: group.getOwner().getUserId();
        this.servers = (group.getServers() == null)? 0: group.getServers();
        this.threshold = (group.getThreshold() == null)? 0: group.getThreshold();
    }
}
