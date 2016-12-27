# Grouper's API Document
This is the REST API document of Grouper Web service, which is a group finance manager application using multiple untrusted servers.

1. Group
====
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
         - oid(String): user id from facebook of group owner
   - error:
      - ErrorKeyWrong(903): Cannot get group info, master key or access key is wrong.

(3)`group/restore`

   - Restore an untrusted server and get group information.
   - method: POST
   - param:
      - accesskey(String): access key of group member
      - uid(String): user id from facebook
   - return:
      - owner(boolean): this user is owner of the group or not
      - group(GroupBean): group information
         - id(String): group id
         - name(String): group name
         - members(int): number of group members
         - createDate(long)
         - oid(String): user id from facebook of group owner
   - error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorUserNotInGroup(1031): This user is not in the group with this access key.

2. User
====
(1)`user/add`

   - Add a new user in this untrusted server.
   - method: POST
   - header:
      - key(String): master key
   - param: 
      - uid(String) : user id from facebook
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
         - id(String): user id from facebook
         - name(String)
         - email(String)
         - gender(String)
         - url(String): the picture url of user's avatar
         - gid(String): group id of this user's group
   -  error:
      - ErrorAccessKey(903): Master key or access key is wrong.
      
3. Transfer
====
(1)`transfer/put`

   - Put a share to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
   - param:
      - share(String): the content of a share
      - receiver(String): user id from facebook of the receiver, it's empty if send to all
   - return:
      - success(boolean)
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorNoReceiverFound(3011): Cannot find receiver in this group by this user id.
      - ErrorPutShare(3012): Put share internel error.

(2)`transfer/list`

   - Put a share to transfer table
   - method: POST
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
   - return example:
      
		{
		  "result": {
		    "contents": [
		      {
		        "result": "notFound",
		        "data": null
		      },
		      {
		        "result": "found",
		        "data": {
		          "id": "05b3df03592f7b7601592f7e3bbe0002",
		          "share": "3498y2fhi34o12834y124y283d88d23d",
		          "savetime": 1482560519000,
		          "sender": "221789398251305",
		          "receiver": "2315426890909763"
		        }
		      },
		      {
		        "result": "noPrivilege",
		        "data": null
		      }
		    ]
		  },
		  "status": 200
		}  
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
