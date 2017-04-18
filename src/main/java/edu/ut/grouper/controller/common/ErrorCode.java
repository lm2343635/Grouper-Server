package edu.ut.grouper.controller.common;

public enum ErrorCode {

    ErrorMasterKey(901, "Master key is wrong."),
    ErrorAccessKey(902, "Access key is wrong."),
    ErrorKeyWrong(903, "Master key or access key is wrong."),

    ErrorGroupExsit(1011, "Group id is exist in this server."),
    ErrorGroupRegister(1012, "Register group internel error."),
    ErrorUserNotMatch(1031, "This access key is not your access key, or you are not member of the group."),
    ErrorGroupInitialized(1041, "This group has been initialized before."),

    ErrorAddUser(2011, "Add user internel error."),
    ErrorPushNoPrivilege(2051, "This user has no privilege to push remote notification to the receiver."),

    ErrorNoReceiverFound(3011, "Cannot find receiver in this group by this user id."),
    ErrorPutShare(3012, "Put share internel error."),
    ErrorSendSelfForbidden(3013, "Cannot send share to yourself."),
    ErrorMessageIdShareFormat(3051, "The format of Map<messageId, shareContent> is worong, so that server cannot parse this map.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
