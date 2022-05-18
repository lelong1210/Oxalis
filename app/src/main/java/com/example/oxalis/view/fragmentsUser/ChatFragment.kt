package com.example.oxalis.view.fragmentsUser

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.ChatAdapter
import com.example.oxalis.adapter.TourInfoItemAdapter
import com.example.oxalis.databinding.FragmentChatBinding
import com.example.oxalis.model.Messenger
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseChat
import com.example.oxalis.view.details.DetailTourInfoAdminFragment
import com.example.oxalis.view.login.LoginActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    var onItemClick: ((String) -> Unit)? = null
    private lateinit var firebaseChat: FirebaseChat
    private lateinit var messengerDetailRecyclerView: RecyclerView
    private lateinit var messengerDetailAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        val userInfo = getUserInfo()

        binding.chatName.text = "Oxalis"

        firebaseChat = FirebaseChat(userInfo.id.toString())

        firebaseChat.getMess() { listMessDetail ->
            Log.i("test", "list size = ${listMessDetail.size},${getUserInfo().mail}")
            setMessDetailListRecycler(listMessDetail, getUserInfo())
        }


        binding.btnSend.setOnClickListener {

            val currentTime = getCurrentTime()
            val chatContent = binding.chatContent.text.toString()

            if (chatContent != "") {
                val messenger = Messenger(
                    userInfo.id + "-messenger",
                   "${userInfo.id}",
                    "${userInfo.fullname}",
                    "${userInfo.id}",
                    "${userInfo.fullname}",
                    "$currentTime",
                    "$chatContent"
                )
                val messengerDetail = MessengerDetail(
                    "${userInfo.id + currentTime}",
                    userInfo.id,
                    userInfo.id + "-messenger",
                    chatContent,
                    currentTime,
                    userInfo.fullname
                )
                firebaseChat.setUp(messenger)
                firebaseChat.sendMess(messengerDetail)
                binding.chatContent.setText("")
            }

        }



        return binding.root
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }

    private fun getCurrentTime(): String {
        val myFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return myFormat.format(Date())
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
}