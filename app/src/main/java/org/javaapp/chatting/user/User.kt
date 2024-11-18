package org.javaapp.chatting.user

data class User(
    val  id : String? = null, // 사용자 식별 ID
    val  name : String? = null, // 이름
    val  statusMessage : String? = null, // 상태메시지
)
