package edu.ut.grouper.bean;

import edu.ut.grouper.domain.User;

public class UserBean {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        this.picture = user.getPictureurl();
        this.gid = user.getGroup().getGid();
    }
}
