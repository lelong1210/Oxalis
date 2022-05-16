package com.example.oxalis.view.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.ChatAdapter
import com.example.oxalis.databinding.FragmentDetailChatAdminBinding
import com.example.oxalis.model.Messenger
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseChat
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DetailChatAdminFragment(private val messenger: Messenger, val userInfo: UserInfo) :
    Fragment() {

    private var _binding: FragmentDetailChatAdminBinding? = null
    private val binding get() = _binding!!
    var onClickRepeat:((Boolean)->Unit)?=null
    var updateListChat:((Boolean)->Unit)?=null
    private lateinit var firebaseChat: FirebaseChat
    private lateinit var messengerDetailRecyclerView: RecyclerView
    private lateinit var messengerDetailAdapter: ChatAdapter
    var onClickRemove: ((Fragment) -> Unit)? = null
    var update:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailChatAdminBinding.inflate(inflater, container, false)

        firebaseChat = FirebaseChat(messenger.idUser.toString())

        firebaseChat.getMess() { listMessDetail ->
            if(update){
                setMessDetailListRecycler(listMessDetail, userInfo)
            }

        }
        binding.btnSend.setOnClickListener {
            val currentTime = getCurrentTime()
            val chatContent = binding.chatContent.text.toString()
            val userInfoAdmin = getUserInfoAdmin()

            if (chatContent != "") {
                val messenger = Messenger(userInfo.id + "-messenger", userInfo.id, currentTime,chatContent,userInfoAdmin.fullname)
                val messengerDetail = MessengerDetail(
                    "${userInfo.id + currentTime}",
                   "${userInfoAdmin.id}",
                    userInfo.id + "-messenger",
                    chatContent,
                    currentTime,
                    "${userInfoAdmin.fullname}"
                )
                firebaseChat.setUp(messenger)
                firebaseChat.sendMess(messengerDetail)
                binding.chatContent.setText("")
            }
        }

        return binding.root
    }

    private fun setMessDetailListRecycler(
        listMessengerDetail: List<MessengerDetail>,
        userInfo: UserInfo
    ) {
        messengerDetailRecyclerView = binding.chatRecyclerView
        messengerDetailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        messengerDetailAdapter = ChatAdapter(requireContext(), listMessengerDetail, userInfo)
        messengerDetailRecyclerView.adapter = messengerDetailAdapter
        messengerDetailRecyclerView.scrollToPosition(messengerDetailRecyclerView.adapter!!.itemCount - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        update = false
    }
    private fun getCurrentTime(): String {
        val myFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return myFormat.format(Date())
    }
    private fun getUserInfoAdmin(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }


}