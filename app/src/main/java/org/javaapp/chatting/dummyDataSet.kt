package org.javaapp.chatting

import org.javaapp.chatting.chatmsg.ChatMsg
import org.javaapp.chatting.chatroomlist.ChatRoom
import org.javaapp.chatting.userlist.User

val userList : List<User> = mutableListOf(
    User("1", "user01", "status msg 01"),
    User("2", "user02", "status msg 02"),
    User("3", "user03", "status msg 03"),
    User("4", "user04", "status msg 04"),
    User("5", "user05", "status msg 05"),
    User("6", "user06", "status msg 06"),
    User("7", "user07", "status msg 07"),
    User("8", "user08", "status msg 08"),
    User("9", "user09", "status msg 09"),
    User("10", "user10", "status msg 10"),
    User("11", "user11", "status msg 11"),
    User("12", "user12", "status msg 12"),
    User("13", "user13", "status msg 13"),
)

val chatRoomList : List<ChatRoom> = mutableListOf(
    ChatRoom("1", "user01", "last msg 01"),
    ChatRoom("2", "user02", "last msg 02"),
    ChatRoom("3", "user03", "last msg 03"),
    ChatRoom("4", "user04", "last msg 04"),
    ChatRoom("5", "user05", "last msg 05"),
    ChatRoom("6", "user06", "last msg 06"),
    ChatRoom("7", "user07", "last msg 07"),
    ChatRoom("8", "user08", "last msg 08"),
    ChatRoom("9", "user09", "last msg 09"),
    ChatRoom("10", "user10", "last msg 10"),
)

val chatMsgList : List<ChatMsg> = mutableListOf(
    ChatMsg("1", "user01", " msg 01"),
    ChatMsg("2", "user02", " msg 02"),
    ChatMsg("3", "user03", " msg 03"),
    ChatMsg("4", "user04", " msg 04"),
    ChatMsg("5", "user05", " msg 05"),
    ChatMsg("6", "user06", " msg 06"),
    ChatMsg("7", "user07", " msg 07"),
    ChatMsg("8", "user08", " msg 08"),
    ChatMsg("9", "user09", " msg 09"),
    ChatMsg("10", "user10", " msg 10"),
)
