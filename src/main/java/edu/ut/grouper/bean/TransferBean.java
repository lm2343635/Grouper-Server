package edu.ut.grouper.bean;

import edu.ut.grouper.domain.Transfer;

import java.util.Date;

public class TransferBean {

    private String messageId;
    private String share;
    private long savetime;
    private String sender;
    private String receiver;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public long getSavetime() {
        return savetime;
    }

    public void setSavetime(long savetime) {
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
        this.messageId = transfer.getMessageId();
        this.share = transfer.getShare();
        this.savetime = transfer.getSavetime();
        this.sender = transfer.getSender().getUid();
        this.receiver = (transfer.getReceiver() == null)? null: transfer.getReceiver().getUid();
    }
}
