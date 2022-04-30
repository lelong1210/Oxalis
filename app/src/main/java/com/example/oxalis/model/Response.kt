package com.example.oxalis.model

data class UserInfo(
    var id: String? = null,
    var fullname: String? = null,
    var mail: String? = null,
    var phone: String? = null,
    var gender: String? = null,
    var permission: String? = "user",
    var avatar: String? = "https://firebasestorage.googleapis.com/v0/b/projectoxalis.appspot.com/o/avatar.png?alt=media&token=a8e069b4-34b4-43d7-834b-7d0a128f5f7a",
    var date: String? = null,
    var address: String? = null
)

val arrayNameOfUserInfo =
    arrayOf("id", "fullname", "mail", "phone", "gender", "permission", "avatar", "date", "address")


data class TourInfo(
//    var id: String? = null,
//    var status: String? = null,
    var name: String? = null,
//    var minPrice: String? = null,
//    var maxPrice: String? = null,
//    var timeStart: String? = null,
//    var timeEnd: String? = null,
//    var age: Int? = null,
    var price: String? = null,
    var avatar: Int? = null,
    var rate: Int? = null,
//    var Describe: String? = null,
    var Address: String? = null,
//    var listImage: ArrayList<String>? = null

)

data class AllCategory(
    val categoryTitle: String? = null,
    val categoryItemList: List<TourInfo>
)