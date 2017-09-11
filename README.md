# API-Server
This project is developed and deployed in cloud instance in 10 hours.

A total of six specified operations have been implemented in the cloud instance.
Kindly follow the input format as provided in the sample scenarios in the assessment document in https://gist.github.com/winston/51d26e4587b5e0bbf03fcad558111c08
* For first three opertaions,'keys' in the JSON input request are not enclosed in quotes. for example: email : 'abc@xyz.com'       Hence, they are handled as string in the code.
* For the last three operations,'keys' in the JSON input request are enclosed in quotes. for example: "email" : 'abc@xyz.com'.
  Hence, the JSon is converted to Map and handled.

1. As a user, I need an API to create a friend connection between two email addresses.
   * The operation is avilable at http://104.238.128.7:8080/makeFriends
   * Before adding, a condition which is one person should not have blocked the another person is checked.This is as 
     per expectaion in operation 5.
   * If the condition is satisfied, they would be connected as friends.
   * Error scenarios like trying to make two persons as friends who are already friends are also handled.
     success flag would be false for this scenario.
   
2. As a user, I need an API to retrieve the friends list for an email address.
   * The operation is avilable at http://104.238.128.7:8080/getFriends
   * As expected, all members who are friends of the specified email will be displayed.
   * Incase if there are no friends, empty list would be displayed. 
   
3. As a user, I need an API to retrieve the common friends list between two email addresses.
   * The operation is avilable at http://104.238.128.7:8080/getCommonFriends
   * As expected, all members who are common friends of the two specified people will be displayed.
   * Incase if there are no common friends, empty list would be displayed.

4. As a user, I need an API to subscribe to updates from an email address.
   * The operation is avilable at http://104.238.128.7:8080/subscribeToUpdates
   * As expected, the requestor would subscribe to updates from the target person.
   * Error scenarios when requestor and target values are empty have been handled. 
     success flag would be false for this scenario.
   
5. As a user, I need an API to block updates from an email address.
   * The operation is avilable at http://104.238.128.7:8080/blockUpdates
   * As expected, the requestor would block the updates from target person if they are friends.
   * Additionally if they are not friends, the requestor would block the target person itself so that they cannot make 
     friend requests in the future.
   
6. As a user, I need an API to retrieve all email addresses that can receive updates from an email address.
   * The operation is avilable at http://104.238.128.7:8080/updatesReceiving
   * All email address that has not blocked updates from the specified "sender" in the request and any of the 
     person who is either a friend or a follower or mentioned in "text" in request are displayed.
   

