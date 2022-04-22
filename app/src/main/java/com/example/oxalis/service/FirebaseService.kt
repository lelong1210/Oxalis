package com.example.oxalis.service

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.example.oxalis.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class FirebaseService {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

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
                    db.collection("users").add(userInfo)
                        .addOnSuccessListener {
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

}


































