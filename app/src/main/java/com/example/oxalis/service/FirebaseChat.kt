package com.example.oxalis.service

import android.util.Log
import com.example.oxalis.model.Messenger
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FirebaseChat(private val userId:String) {
    private val database = Firebase.database
    private val messengerDb = database.getReference("message")
    private val messengerDetailDb = database.getReference(userId+"messageDetail")

    fun setUp(messenger: Messenger) {
        messengerDb.child(messenger.id.toString()).setValue(messenger)
    }

    fun sendMess(messengerDetail: MessengerDetail) {
        messengerDetailDb.child(messengerDetail.id.toString()).setValue(messengerDetail)
    }

    fun getMess(callback: (listMessDetail:List<MessengerDetail>) -> Unit) {
        messengerDetailDb.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList = ArrayList<MessengerDetail>()
                for (postSnapshot in snapshot.children) {
                    val messengerDetail = postSnapshot.getValue(MessengerDetail::class.java)
                    arrayList.add(messengerDetail!!)
                }
                callback.invoke(arrayList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("test", "Failed to read value.", error.toException())
            }

        })
    }
    fun getListMessAdmin(callback: (listMess: List<Messenger>) -> Unit){
        messengerDb.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList = ArrayList<Messenger>()
                for (postSnapshot in snapshot.children) {
                    val messenger = postSnapshot.getValue(Messenger::class.java)
                    arrayList.add(messenger!!)
                }
                callback.invoke(arrayList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("test", "Failed to read value.", error.toException())
            }

        })
    }

}