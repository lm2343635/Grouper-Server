# Grouper's Web Service

## Remote Notification Certificate

To push a remote notification to iOS devices, the developer should prepare a p12 certificate file as follows.

### 1. Create a CSR file.

In the Applications folder on your Mac, open the Utilities folder and launch Keychain Access.

Within the Keychain Access drop down menu, select Keychain Access > Certificate Assistant > Request a Certificate from a Certificate Authority.

- In the Certificate Information window, enter the following information:
	- In the User Email Address field, enter your email address.
	- In the Common Name field, create a name for your private key (e.g., John Doe Dev Key).
	- The CA Email Address field should be left empty.
	- In the "Request is" group, select the "Saved to disk" option.
- Click Continue within Keychain Access to complete the CSR generating process.

### 2. Apply aps.cer file

- Upload the CertificateSigningRequest.certSigningRequest file generated as describe in Step 1 to https://developer.apple.com/ios/manage/certificates/team/index.action.
- Apply a file named aps.cer for your application

### 3. Export key.p12 file

- Click the aps.cer file and find it in the Keychain Access application.
- Select the private key, the name of the private key is same as the **Common Name field** when you created the CSR file.
- Export the priviate key as a key.p12 file.
- Remember the password of this key.p12 file.

### 4. Generate aps.p12 file

- Put the aps.cer and key.p12 files into a same folder.
- Generate aps.pem file

```shell
$ openssl x509 -in aps.cer -inform DER -out aps.pem -outform PEM
```
- Generate key.pem file. 
	- Input the password of the key.p12 file
	- Input a new password for the key.pem file.

```shell
$ openssl pkcs12 -nocerts -out key.pem -in key.p12
Enter Import Password:
MAC verified OK
Enter PEM pass phrase:
Verifying - Enter PEM pass phrase:
```

- Generate aps.p12 file. 
	- Input the password of the key.pem file
	- Input a new password for the aps.p12 file.

```shell
$ openssl pkcs12 -export -in aps.pem -inkey key.pem -name "push" -out aps.p12
Enter pass phrase for key.pem:
Enter Export Password:
Verifying - Enter Export Password:$
```


## API Document

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

   - Initialize a group by submitting the number of servers and recover threshold.
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

   - Add a new user in this untrusted server.
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
      - key(String): access key of group member
   - return:
      - ok(boolean): user can access this user or not

(3)`user/deviceToken`

   - Update device's device token of a user.
   - method: POST
   - header:
      - key(String): access key of group member
   - param: 
      - deviceToken(String): device token from APNs server
   - return:
      - success(boolean)

(4)`user/notify`

   - Notify a receiver with a message.
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

   - Put a share to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
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
      - success(int): the number of shares which are save successfully
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

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

   - Send messageIds and get messageIds which are not existed in this untrusted server.
   - method: POST
   - header:
      - key(String): access key of group member
   - param:
      - messageId(List\<String>): messageId list, submit by messageId=xxx&messageId=xxx&messageId=xxx
   - return:
      - messageIds(List\<String>): messageIds which are not existed in this untrusted server
