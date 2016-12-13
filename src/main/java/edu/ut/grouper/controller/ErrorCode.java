package edu.ut.grouper.controller;

/**
 * Created by limeng on 12/12/2016.
 */
public enum ErrorCode {

    ErrorMasterKey(901, "Master key is wrong."),
    ErrorAccessKey(902, "Access key is wrong."),
    ErrorKeyWrong(903, "Master key or access key is wrong."),

    ErrorGroupExsit(1011, "Group id is exist in this server."),
    ErrorGroupRegister(1012, "Register group internel error."),

    ErrorAddUser(2011, "Add user internel error."),

    ErrorNoReceiverFound(3011, "Cannot find receiver in this group by this user id."),
    ErrorPutShare(3012, "Put share internel error."),
    ErrorSendSelfForbidden(3013, "Cannot send share to yourself."),
    ErrorNoShareFound(3031, "Cannot find share by this id."),
    ErrorShareNoPrivilege(3032, "You do not have privilege to access this share.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
