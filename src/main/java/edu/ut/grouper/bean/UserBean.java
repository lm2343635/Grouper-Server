package edu.ut.grouper.bean;

import edu.ut.grouper.domain.User;

public class UserBean {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String url;
    private String gid;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public UserBean(User user) {
        this.id = user.getUid();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.url = user.getPictureurl();
        this.gid = user.getGroup().getGid();
    }
}
