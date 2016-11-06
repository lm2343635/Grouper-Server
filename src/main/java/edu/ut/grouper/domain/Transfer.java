package edu.ut.grouper.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transfer")
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String tid;
    @Column(nullable = false)
    private String sid;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String object;
    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false)
    private Date savetime;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSavetime() {
        return savetime;
    }

    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }
}
