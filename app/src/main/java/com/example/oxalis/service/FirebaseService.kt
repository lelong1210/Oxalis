package com.example.oxalis.service

import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.example.oxalis.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.w3c.dom.Document

class FirebaseService {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val tableUsers = db.collection("users")
    private val tableTour = db.collection("tours")
    private val tableLastId = db.collection("lastId")
    private val tableStopPoint = db.collection("stopPoint")
    private val tableSheetAddInformationCart = db.collection("sheetAddInformationCart")
    private val tableDiscount = db.collection("discount")

    // check login
    fun isLogin(): Boolean {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            return true
        }
        return false
    }

    fun createAccountAuth(
        userInfo: UserInfo,
        password: String,
        callback: (status: Boolean) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(userInfo.mail!!, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // get UID
                    userInfo.id = auth.uid
                    // add user to users in firebase
                    tableUsers.document(userInfo.id.toString()).set(userInfo)
                        .addOnSuccessListener {
                            Log.i("test", "password: $password")
                            callback.invoke(true)
                        }.addOnFailureListener {
                            callback.invoke(false)
                        }
                } else {
                    callback.invoke(false)
                }
            }
            .addOnFailureListener { e ->
                callback.invoke(false)
            }
    }

    fun login(mail: String, password: String, callback: (userInfo: UserInfo) -> Unit) {
        var userInfo: UserInfo
        auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                tableUsers.document(auth.uid.toString()).get().addOnCompleteListener { task ->
                    userInfo = task.result.toObject(UserInfo::class.java)!!
                    callback.invoke(userInfo)
                }
            } else {
                val userInfo = UserInfo(
                    null,
                    "",
                    "",
                    "",
                    "",
                    "user",
                    "",
                    "",
                    ""
                )
                callback.invoke(userInfo)
            }
        }
    }

    fun sendPasswordResetEmail(
        mail: String,
        callback: (status: Boolean) -> Unit
    ) {
        auth.sendPasswordResetEmail(mail).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(true)
            } else {
                callback.invoke(false)
            }
        }
    }

    fun insertTourInfo(tourInfo: TourInfo, callback: (status: Boolean) -> Unit) {
        tableTour.document(tourInfo.id.toString()).set(tourInfo)
            .addOnCompleteListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun getLastIdOfStopPoint(callback: (lastId: String) -> Unit) {
        tableLastId.document("LastOfIdStopPoint").get().addOnCompleteListener { task ->
            var lastIdOfStopPoint = task.result.getString("lastId")
            callback.invoke(lastIdOfStopPoint!!)
/*            tableLastId.document("LastOfIdStopPoint")
                .update("lastId", "${lastIdOfStopPoint?.plus(1)}")
                .addOnCompleteListener { taskChild ->
                    Log.i("test", "${taskChild.isSuccessful}")
                }*/
        }
    }

    fun updateLastIdOfStopPoint(lastIdOfStopPoint: Int, callback: (status: Boolean) -> Unit) {
        tableLastId.document("LastOfIdStopPoint")
            .update("lastId", "${lastIdOfStopPoint?.plus(1)}")
            .addOnCompleteListener { taskChild ->
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun insertStopPoint(stopPointInfo: StopPointInfo, callback: (status: Boolean) -> Unit) {
        tableStopPoint.document(stopPointInfo.id.toString()).set(stopPointInfo)
            .addOnCompleteListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun getStopPoint(
        idStopPoint: String,
        callback: (stopPointInfo: StopPointInfo) -> Unit
    ) {
        tableStopPoint.document(idStopPoint).get().addOnCompleteListener { task ->
            val stopPointInfo = task.result.toObject(StopPointInfo::class.java)!!
            callback.invoke(stopPointInfo)
        }
    }

    fun getAllStopPoint(callback: (stopPointInfoList: List<StopPointInfo>) -> Unit) {
        tableStopPoint.get().addOnSuccessListener { result ->
            var arrayListStopPointInfo = ArrayList<StopPointInfo>()
            for (document in result) {
                var stopPointInfo = document.toObject(StopPointInfo::class.java)
                arrayListStopPointInfo.add(stopPointInfo)
            }
            callback.invoke(arrayListStopPointInfo)
        }
    }

    fun getAllSheetBookTour(callback: (sheetAddInformationList: List<SheetAddInformationCart>) -> Unit) {
        tableSheetAddInformationCart.get().addOnSuccessListener { result ->
            var arrayListSheetAddInformationCart = ArrayList<SheetAddInformationCart>()
            for (document in result) {
                var sheetAddInformationCart = document.toObject(SheetAddInformationCart::class.java)
                arrayListSheetAddInformationCart.add(sheetAddInformationCart)
            }
            callback.invoke(arrayListSheetAddInformationCart)
        }
    }

    fun getAllDiscount(callback: (discountList: List<Discount>) -> Unit) {
        tableDiscount.get().addOnSuccessListener { result ->
            var arrayListDiscount = ArrayList<Discount>()
            for (document in result) {
                var discount = document.toObject(Discount::class.java)
                arrayListDiscount.add(discount)
            }
            callback.invoke(arrayListDiscount)
        }
    }

    fun deleteDiscount(idOfDiscount:String,callback: (status: Boolean) -> Unit){
        tableDiscount.document(idOfDiscount).delete().addOnSuccessListener  {
            callback.invoke(true)
        }.addOnFailureListener {
            callback.invoke(false)
        }
    }

    //        }
//        callback.invoke(stopPointInfo)

    fun selectImage() {

    }

    fun updatePassword(newPassword: String, callback: (status: Boolean) -> Unit) {
        val user = auth.currentUser
        user!!.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(true)
            } else {
                callback.invoke(false)
            }
        }
    }

    fun uploadImage(
        nameOfImage: String,
        arrayImageUri: ArrayList<Uri>,
        callback: (status: Boolean) -> Unit
    ) {
        var index = 0
        for (i in 0 until arrayImageUri.size) {
            var imageRef =
                FirebaseStorage.getInstance().reference.child("image/${nameOfImage + arrayOfImage[i]}.jpg")
            imageRef.putFile(arrayImageUri[i]).addOnSuccessListener {
                Log.i("test", "${nameOfImage + arrayOfImage[i]}")
                index++
                if (index == arrayOfImage.size) {
                    callback.invoke(true)
                }
            }
        }
    }

    fun getOnlyImage(nameOfImage: String, callback: (uriOfImage: Uri) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        storageRef.child("image/${nameOfImage}0.jpg").downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(task.result)
            }
        }
    }

    fun getUrlImage(
        nameOfImage: String,
        arrayList: ArrayList<String>,
        callback: (arrayUri: ArrayList<Uri>) -> Unit
    ) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val arrayListUri = ArrayList<Uri>()
        var index = 0
        for (i in 0 until arrayList.size) {
            storageRef.child("image/${nameOfImage + arrayList[i]}.jpg").downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    arrayListUri.add(task.result)
                    index++
                    if (index == arrayList.size) {
                        callback.invoke(arrayListUri)
                    }
                }
            }
        }
    }

    fun getLastIdOfTourInfo(callback: (lastId: String) -> Unit) {
        tableLastId.document("LastOfIdTour").get().addOnCompleteListener { task ->
            var lastIdOfTourInfo = task.result.getString("lastId")
            callback.invoke(lastIdOfTourInfo!!)
        }
    }

    fun getLastId(document: String, field: String, callback: (lastId: String) -> Unit) {
        tableLastId.document(document).get().addOnCompleteListener { task ->
            var lastId = task.result.getString(field)
            callback.invoke(lastId!!)
        }
    }

    fun updateLastId(
        document: String,
        field: String,
        lastIdOfTourInfo: Int,
        callback: (status: Boolean) -> Unit
    ) {
        tableLastId.document(document)
            .update(field, "${lastIdOfTourInfo?.plus(1)}")
            .addOnCompleteListener { taskChild ->
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun insertSheetAddInformationCart(
        sheetAddInformationCart: SheetAddInformationCart,
        callback: (status: Boolean) -> Unit
    ) {
        tableSheetAddInformationCart.document(sheetAddInformationCart.id.toString())
            .set(sheetAddInformationCart).addOnCompleteListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun insertDiscount(
        discount: Discount,
        callback: (status: Boolean) -> Unit
    ) {
        tableDiscount.document(discount.id.toString())
            .set(discount).addOnCompleteListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun getDiscount(discountId: String, callback: (discount:Discount) -> Unit) {
        tableDiscount.document(discountId).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                if(task.result.exists()){
                    var discount = task.result.toObject(Discount::class.java)
                    callback.invoke(discount!!)
                }else{
                    val discount = Discount("","","")
                    callback.invoke(discount!!)
                }
            }
        }
    }

    fun updateLastIdOfTourInfo(lastIdOfTourInfo: Int, callback: (status: Boolean) -> Unit) {
        tableLastId.document("LastOfIdTour")
            .update("lastId", "${lastIdOfTourInfo?.plus(1)}")
            .addOnCompleteListener { taskChild ->
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun getAllTourInfo(callback: (tourInfoList: List<TourInfo>) -> Unit) {
        tableTour.get().addOnSuccessListener { result ->
            var arrayListTourInfo = ArrayList<TourInfo>()
            for (document in result) {
                var tourInfo = document.toObject(TourInfo::class.java)
                arrayListTourInfo.add(tourInfo)
            }
            callback.invoke(arrayListTourInfo)
        }
    }
/*//                    Log.i("test","path ====> ${task.result}")
//                    Glide.with(this).load(task.result).into(binding.firebaseimage1)*/
}



















