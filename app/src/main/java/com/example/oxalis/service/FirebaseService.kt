package com.example.oxalis.service

import android.util.Log
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayNameOfUserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

class FirebaseService {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val tableUsers = db.collection("users")

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

        auth.createUserWithEmailAndPassword(userInfo.mail, password)
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
        auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var userInfo: UserInfo
                val arrayList = ArrayList<String>()
                tableUsers.document(auth.uid.toString()).get().addOnCompleteListener { task ->
                    for (i in arrayNameOfUserInfo.indices) {
                        arrayList.add(task.result.getString(arrayNameOfUserInfo[i]).toString())
                    }
                    userInfo = UserInfo(
                        arrayList[0],
                        arrayList[1],
                        arrayList[2],
                        arrayList[3],
                        arrayList[4],
                        arrayList[5],
                        arrayList[6],
                        arrayList[7],
                        arrayList[8]
                    )
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
}


/*                    UserInfo::class.memberProperties.forEach { member ->
                        arrayList.add(task.result.getString(member.name).toString())
                        Log.i("test",member.name)
                    }*/


















