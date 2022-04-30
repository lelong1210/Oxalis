package com.example.oxalis.service

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.example.oxalis.R
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayNameOfUserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

class FirebaseService {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val tableUsers = db.collection("users")
    private val tableTour = db.collection("tours")

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
    fun insertTourInfo(tourInfo: TourInfo,callback: (status: Boolean) -> Unit){

    }
    fun selectImage(){

    }
}



















