# Grouper, API Document
This is the REST API document of Groupr web service, which is a group finance manager application using mutiple untrusted servers.

1. Transfer
====
(1)`transfer/send`

   - method: POST
   - param: 
      - sid(String) : physical id in client
      - content(String): a share
      - object(String): class name of a object
   - return
      - count(int): sync times in this server
