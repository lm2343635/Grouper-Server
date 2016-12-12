package edu.ut.grouper.controller;

/**
 * Created by limeng on 12/12/2016.
 */
public enum ErrorCode {

    ErrorGroupExsit(1001, "Group id is exist in this server"),
    ErrorGroupRegister(1002, "Register group internel error"),
    ErrorMasterKey(2001, "Master key is wrong"),
    ErrorAccessKey(2002, "Access key is wrong"),
    ErrorKeyWrong(2003, "Cannot get group info, master key or access key is wrong."),
    ErrorAddUser(2004, "Add user internel error"),
    ErrorNoReceiverFound(3001, "Cannot find receive in this group by this user id."),
    ErrorPutShare(3002, "Put share internel error");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
