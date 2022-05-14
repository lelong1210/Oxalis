package com.example.oxalis.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.ChatAdapter
import com.example.oxalis.databinding.ActivityDetailChatAdminBinding
import com.example.oxalis.databinding.ActivityDetailTourInfoBinding
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseChat

class DetailChatAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailChatAdminBinding
    private lateinit var firebaseChat: FirebaseChat
    private lateinit var messengerDetailRecyclerView: RecyclerView
    private lateinit var messengerDetailAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChatAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messenger = intent.getStringExtra("messenger")
        val userInfo = intent.getStringExtra("userInfo")

        Log.i("test","$messenger ==== $userInfo")

//        firebaseChat = FirebaseChat(messenger.idUser.toString())
//        firebaseChat.getMess() { listMessDetail ->
//            setMessDetailListRecycler(listMessDetail, userInfo)
//        }

    }
    private fun setMessDetailListRecycler(
        listMessengerDetail: List<MessengerDetail>,
        userInfo: UserInfo
    ) {
        messengerDetailRecyclerView = binding.chatRecyclerView
        messengerDetailRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        messengerDetailAdapter = ChatAdapter(applicationContext, listMessengerDetail, userInfo)
        messengerDetailRecyclerView.adapter = messengerDetailAdapter
        messengerDetailRecyclerView.scrollToPosition(messengerDetailRecyclerView.adapter!!.itemCount - 1)
    }
}