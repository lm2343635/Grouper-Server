package edu.ut.grouper.bean;

import edu.ut.grouper.domain.Transfer;

import java.util.Date;

/**
 * Created by limeng on 13/12/2016.
 */
public class TransferBean {

    private String id;
    private String share;
    private Date savetime;
    private String sender;
    private String receiver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public Date getSavetime() {
        return savetime;
    }

    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public TransferBean(Transfer transfer) {
        this.id = transfer.getTid();
        this.share = transfer.getShare();
        this.savetime = transfer.getSavetime();
        this.sender = transfer.getSender().getUid();
        this.receiver = (transfer.getReceiver() == null)? null: transfer.getReceiver().getUid();
    }
}
