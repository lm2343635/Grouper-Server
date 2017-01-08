package edu.ut.grouper.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "grouper")
public class Group implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String gid;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String gname;

    @Column(nullable = false)
    private Integer members;

    @Column(nullable = false)
    private String masterkey;

    @Column(nullable = false)
    private Long createDate;

    @Column
    private Integer servers;

    @Column
    private Integer threshold;

    @ManyToOne
    @JoinColumn(name = "owner", unique = true)
    private User owner;

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

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getMasterkey() {
        return masterkey;
    }

    public void setMasterkey(String masterkey) {
        this.masterkey = masterkey;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getServers() {
        return servers;
    }

    public void setServers(Integer servers) {
        this.servers = servers;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
