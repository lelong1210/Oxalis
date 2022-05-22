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
    var address: String? = null,
    var status: String? = null
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
    var permission: String? = null,
    var avatar: String? = null,
    var rate: String? = null,
    var describe: String? = null,
    var type: String? = null,
    var discount: String? = null,
    var listImage: ArrayList<String>? = null,
) : Serializable

data class StopPointInfo(
    var id: String? = null,
    var name_of_address: String? = null,
    var typeOfService: String? = null,
    var Address: String? = null,
    var price: String? = null,
    var avatar: String? = null,
    var timeStart: String? = null,
    var timeEnd: String? = null,
    var listImage: ArrayList<String>? = null,
    var contract: String? = null,
    var describe: String? = null

) : Serializable

data class Discount(
    var id: String? = null,
    var percentDiscount: String? = null,
    var numberAvailability: String? = null
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

data class SheetAddInformationCart(
    var id: String? = null,
    var idUser: String? = null,
    var nameOfUserBookTour: String? = null,
    var idTour: String? = null,
    var nameOfTour: String? = null,
    var gender: String? = null,
    var mail: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var timeStart: String? = null,
    var numberOfPeople: String? = null,
    var status: String? = null,
    var discountCode: String? = null,
    var price: String? = null
)

data class Messenger(
    var id: String? = null,
    var idUser: String? = null,
    var nameOfUser: String? = null,

    var idUserSend:String?=null,
    var namOfUserLastSend:String?=null,
    var timeLastSend: String? = null,
    var messLastSend: String? = null,

)

data class MessengerDetail(
    var id: String? = null,
    var idUserSend: String? = null,
    var idMessenger: String? = null,
    var content: String? = null,
    var timeSend: String? = null,
    var nameUserSend: String? = null,
)

data class RatingTour(
    var id: String? = null,
    var idTour: String? = null,
    var idUser: String? = null,
    var nameUser:String?=null,
    var content: String? = null,
    var rate: String? = null,
    var timeRating: String? = null,
    var display: String? = null
)

data class ReplyRatingTour(
    var id: String? = null,
    var idAdmin: String? = null,
    var nameOfAdmin:String?=null,
    var idRatingTour: String? = null,
    var content: String?=null,
    var timeReply:String?=null
)

data class Province(
    var id: String?=null,
    var name: String?=null,
    var list: ArrayList<District>?=null
)
data class District(
    var id: String?=null,
    var name: String?=null,
    var list: ArrayList<Ward>?=null
)
data class Ward(
    var id: String?=null,
    var name:String?=null,
)

val arrayOfImage = arrayListOf("0", "1", "2", "3")
val arrayOfStatusSheet = arrayListOf("CHƯA XÁC NHẬN", "ĐÃ XÁC NHẬN", "ĐÃ HỦY")
val arrayStatusTour = arrayListOf("CÓ THỂ ĐẶT", "KHÔNG THỂ ĐẶT")
val arrayDisplay = arrayListOf("ẨN", "HIỆN")
val arrayItemTypeTour = arrayListOf("THAM QUAN", "VĂN HÓA", "MẠO HIỂM", "ẨM THỰC", "TEAMBUILDING")
val arrayStatusUserInfo = arrayListOf("KHÓA", "HOẠT ĐỘNG")
val arrayGender = arrayListOf("NAM", "NỮ")
val arrayPermission = arrayListOf("user", "admin")
