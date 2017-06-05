package edu.ut.grouper.bean;

import edu.ut.grouper.domain.Group;

public class GroupBean {

    private String gid;
    private String id;
    private String name;
    private int members;
    private long createAt;
    private UserBean owner;
    private int servers;
    private int threshold;
    private int interval;

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

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public UserBean getOwner() {
        return owner;
    }

    public void setOwner(UserBean owner) {
        this.owner = owner;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public GroupBean(Group group) {
        this.gid = group.getGid();
        this.id = group.getId();
        this.name = group.getGname();
        this.members = group.getMembers();
        this.createAt = group.getCreateAt();
        this.owner = (group.getOwner() == null) ? null : new UserBean(group.getOwner());
        this.servers = (group.getServers() == null) ? 0 : group.getServers();
        this.threshold = (group.getThreshold() == null) ? 0 : group.getThreshold();
        this.interval = (group.getInterval() == null) ? 0 : group.getInterval();
    }

}
