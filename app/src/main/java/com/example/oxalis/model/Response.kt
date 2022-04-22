package com.example.oxalis.model

data class UserInfo(
    var id: String? = null,
    var fullname: String? = null,
    var mail: String,
    var phone: String? = null,
    var gender: String? = null,
    var permission: String? = "user",
    var avatar: String? = "https://firebasestorage.googleapis.com/v0/b/projectoxalis.appspot.com/o/avatar.png?alt=media&token=a8e069b4-34b4-43d7-834b-7d0a128f5f7a",
    var date: String? = null,
    var address: String? = null
)
