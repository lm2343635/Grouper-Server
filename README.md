# Grouper's API Document
This is the REST API document of Grouper Web service. Grouper is a framwework for creating mobile applications based on data syncrhonization using multiple unstrusted servers.

### 1. Group

(1)`group/register`
   
   - Register a new group in this untrusted server.
   - method: POST
   - param: 
      - id(String): group id
      - name(String): group name
   - return:
      - masterkey(String): master key for group creator
   - error:
      - ErrorGroupExsit(1011): Group id is exist in this server.
      - ErrorGroupRegister(1012): Register group error.

(2)`group/info`
   
   - Get group information.
   - method: GET
   - header:
      - key(String): master key of this group or access key of group member
   - return:
      - group(GroupBean): group information
         - id(String): group id
         - name(String): group name
         - members(int): number of group members
         - createDate(long)
         - oid(String): userId from facebook of group owner
         - servers(int): number of untrusted servers
         - threshold(int): recover threshold
   - error:
      - ErrorKeyWrong(903): Cannot get group info, master key or access key is wrong.

(3)`group/restore`

   - Restore an untrusted server and get group information.
   - method: POST
   - param:
      - accesskey(String): access key of group member
      - uid(String): userId from facebook
   - return:
      - owner(boolean): this user is owner of the group or not
      - group(GroupBean): group information
         - id(String): group id
         - name(String): group name
         - members(int): number of group members
         - createDate(long)
         - oid(String): userId from facebook of group owner
         - servers(int): number of untrusted servers
         - threshold(int): recover threshold
   - error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorUserNotInGroup(1031): This user is not in the group with this access key.

(4)`group/init`

   - Initialize a group by submitting the number of servers and recover threshold.
   - method: POST
   - param:
      - server(int): the number of servers
      - threshold(int): recover threshold
   - return:
      - success(boolean)
   - error:
      - ErrorMasterKey(901): Master key is wrong.
      - ErrorGroupInitialized(1041): This group has been initialized before.

### 2. User

(1)`user/add`

   - Add a new user in this untrusted server.
   - method: POST
   - header:
      - key(String): master key
   - param: 
      - userId(String) : userId from facebook
      - name(String)
      - email(String)
      - gender(String)
      - pictureUrl(String): the picture url of user's avatar
      - owner(boolean): this user is group owner or not
   - return:
      - accesskey(String): access key for this new user
   -  error:
      - ErrorMasterKey(901): Master key is wrong.
      - ErrorAddUser(2011): Add user internel error.

(2)`user/list`

   - Get all users' information of a group.
   - method: GET
   - header:
      - key(String): master key of this group or access key of group member
   - return:
      - users(List<UserBean>): user list of this group
         - userId(String): userId from facebook
         - name(String)
         - email(String)
         - gender(String)
         - url(String): the picture url of user's avatar
         - gid(String): group id of this user's group
   - error:
      - ErrorAccessKey(903): Master key or access key is wrong.

(3)`user/state`

   - Check server state.
   - method: GET
   - header:
      - key(String): access key of group member
   - return:
      - ok(boolean): user can access this user or not

(4)`user/deviceToken`

   - Update device's device token of a user.
   - method: POST
   - header:
      - key(String): access key of group member
   - param: 
      - deviceToken(String): device token from APNs server
   - return:
      - success(boolean)

(5)`user/notify`

   - Notify a receiver with a message.
   - method: POST
   - header:
      - key(String): access key of group member
   - param: 
      - content(String): message content
      - receiver(String): receiver's userId. Use "*" if notify all group members.
   - return:
      - success(boolean)
   - error:
      - ErrorAccessKey(903): Master key or access key is wrong.
      - ErrorPushNoPrivilege(2051): This user has no privilege to push remote notification to the receiver.
   
### 3. Transfer

(1)`transfer/put`

   - Put a share to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
   - param:
      - share(String): the content of a share
      - receiver(String): userId of the receiver, it's "*" if send to all
      - messageId(String): messageId of the message which can be recovered by this share
   - return:
      - success(boolean)
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorNoReceiverFound(3011): Cannot find receiver in this group by this userId.
      - ErrorPutShare(3012): Put share internal error.
      - ErrorSendSelfForbidden(3013): Cannot send share to yourself.

(2)`transfer/list`

   - Put a share to transfer table
   - method: GET
   - header:
      - key(String): access key of group member
   - return:
      - shares(List\<String>): id of shares
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

(3)`transfer/get`

   - Get a share list by access key, only get share for this user or all users.
   - method: GET
   - header:
      - key(String): access key of group member
   - param:
      - id(List\<String>): id list of shares, submit by id=xxx&id=xxx&id=xxx
   - return:
      - content(List\<Map>): 
         - result(String): result for this id: found, not found or no privilege
         - data(TransferBean): share content
         - id: share id
   - return example:
```json
{
  "result": {
    "contents": [
      {
        "result": "notFound",
        "data": null,
        "id": "4028e00059677d820asdasdasdd59002" 
      },
      {
        "result": "found",
        "data": {
          "mid": "ADED87CD-D659-458F-B70D-335F31F042DE",
          "share": "0202B065A3F6F108C964FD900C8004015F4962CC63BD933A978980A6671A3F15000F4BE28D960",
          "savetime": 1482560519000,
          "sender": "221789398251305",
          "receiver": "2315426890909763"
        },
        "id": "4028e00059677d8201596785b1590000"
      },
      {
        "result": "noPrivilege",
        "data": null,
        "id": "4028e000597cfe3601597d15a5ef0004"
      }
    ]
  },
  "status": 200
}  
``` 
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

(4)`transfer/confirm`

(5)`transfer/reput`

   - Reput shares to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
   - param:
      - receiver(String): userId of the receiver, it's "*" if send to all
      - shares(JSON String, Map\<String, String>): messageId-share Map, it is a JSON String like such format
```json
{
	"messageId": "shareContent",
	"messageId": "shareContent"
}
```

   - return:
      - success(boolean)
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorNoReceiverFound(3011): Cannot find receiver in this group by this userId.
      - ErrorPutShare(3012): Put share internal error.
      - ErrorSendSelfForbidden(3013): Cannot send share to yourself.
      - ErrorMessageIdShareFormat(3051): The format of Map<messageId, shareContent> is worong, so that server cannot parse this map.
