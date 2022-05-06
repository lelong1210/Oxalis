package com.example.oxalis.model

import java.io.Serializable

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

data class TourInfo(
    var id: String? = null,
    var status: String? = null,
    var name: String? = null,
    var adrress: String? = null,
    var price: String? = null,
    var timeStart: String? = null,
    var timeOfTour: String? = null,
    var age: String? = null,
    var permission: String?=null,
    var avatar: String? = null,
    var rate: String? = null,
    var describe: String? = null,
    var type:String?=null,
    var discount:String?=null,
    var listImage: ArrayList<String>? = null,
):Serializable


data class StopPointInfo(
    var id: String? = null,
    var name_of_address: String? = null,
    var typeOfService: String? = null,
    var Address: String? = null,
    var price: String?=null,
    var avatar: String? = null,
    var timeStart: String? = null,
    var timeEnd: String? = null,
    var listImage: ArrayList<String>? = null,
    var contract: String? = null,
    var describe:String?=null

):Serializable

data class Discount(
    val image:Int,
    val percentDiscount:Int
)

data class AllCategoryDiscount(
    val categoryTitle: String? = null,
    val categoryItemList: List<Discount>
)

data class AllCategoryTourInfo(
    val categoryTitle: String? = null,
    val categoryItemList: List<TourInfo>
)
data class AllCategoryStopPoint(
    val categoryTitle: String? = null,
    val categoryItemList: List<StopPointInfo>
)
val arrayOfImage = arrayListOf("0","1","2","3")