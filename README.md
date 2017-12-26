# Grouper's API Document
This is the REST API document of Grouper Web service. Grouper is a framwework for creating mobile applications based on data syncrhonization using multiple unstrusted servers.

### 1. Group

(1)`group/register`
   
   - Register a new group in an untrusted server.
   - method: POST
   - param: 
      - id(String): group id
      - name(String): group name
   - return:
      - masterkey(String): master key for the group owner
   - error:
      - ErrorGroupExsit(1011): Group id is exist in this server.
      - ErrorGroupRegister(1012): Register group error.

(2)`group/info`
   
   - Get group information.
   - method: GET
   - header:
      - key(String): master key of this group or access key of the group member
   - return:
      - group(GroupBean): group information
         - id(String): group id
         - name(String): group name
         - members(int): number of group members
         - createAt(long)
         - owner(UserBean): group owner
         - servers(int): number of untrusted servers
         - threshold(int): recover threshold
         - interval(int): delection interval time
   - error:
      - ErrorKeyWrong(903): Cannot get group info, master key or access key is wrong.

(3)`group/restore`

   - Restore an untrusted server and get group information.
   - method: POST
   - param:
      - accesskey(String): access key of group member
      - userId(String): userId from facebook
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
      - ErrorUserNotMatch(1031): This user is not in the group with this access key.

(4)`group/init`

   - Initialize a group by submitting the parameters in the extended secret sharing scheme f(k, n, s) and the TTL.
   - method: POST
   - param:
      - server(int): the number of servers, the parameter n in f(k, n, s)
      - threshold(int): recover threshold, the parameter k in f(k, n, s)
      - safe(int): the number of safe servers, the parameter s in f(k, n, s)
      - interval(int): delection interval time
   - return:
      - success(boolean)
   - error:
      - ErrorMasterKey(901): Master key is wrong.
      - ErrorGroupInitialized(1041): This group has been initialized before.

### 2. User

(1)`user/add`

   - Add a new user in the untrusted server by the group owner.
   - method: POST
   - header:
      - key(String): master key
   - param: 
      - node(String) : node identifier
      - owner(boolean): this user is group owner or not
   - return:
      - accesskey(String): access key for this new user
   -  error:
      - ErrorMasterKey(901): Master key is wrong.
      - ErrorAddUser(2011): Add user internel error.

(2)`user/state`

   - Check server state.
   - method: GET
   - header:
      - key(String): access key of the group member
   - return:
      - ok(boolean): user can access this user or not

(3)`user/deviceToken`

   - Update the device token of the device by a group member.
   - method: POST
   - header:
      - key(String): access key of the group member
   - param: 
      - deviceToken(String): device token from APNs server
   - return:
      - success(boolean)

(4)`user/notify`

   - Send a remote notification to a group member.
   - method: POST
   - header:
      - key(String): access key of group member
   - param: 
      - content(String): message content
      - receiver(String): node identifier of receiver, use "*" if notify all group members.
      - category(String)
   - return:
      - success(boolean)
   - error:
      - ErrorAccessKey(903): Master key or access key is wrong.
      - ErrorPushNoPrivilege(2051): This user has no privilege to push remote notification to the receiver.
   
### 3. Transfer

(1)`transfer/put`

   - Upload a share to the untrusted server.
   - method: POST
   - header:
      - key(String): access key of the group member
   - param:
      - shares(String): JSON array of share, it should be such format:
         - share(String): the content of a share
         - receiver(String): node identifier of receiver, user "*" if send to all
         - messageId(String): messageId of the message which can be recovered by this share
```json
[{
	"share": "",
	"receiver": "",
	"messageId": ""
}, {
	"share": "",
	"receiver": "",
	"messageId": ""
}]
```
      
   - return:
      - success(int): the number of shares which are saved in the server successfully
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

(2)`transfer/list`

   - Get the list of share IDs for a group member.
   - method: GET
   - header:
      - key(String): access key of the group member
   - return:
      - shares(List\<String>): share IDs that this group member has privilege to download
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

(3)`transfer/get`

   - Get the content of shares by the list of share IDs.
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

   - Confirm whether the shares are existed in an untrusted server. 
   - method: POST
   - header:
      - key(String): access key of the group member
   - param:
      - messageId(List\<String>): array of message IDs, submit by messageId=xxx&messageId=xxx&messageId=xxx
   - return:
      - messageIds(List\<String>): array of message IDs which are not existed in this untrusted server
