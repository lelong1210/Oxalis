package com.example.oxalis.view.details

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailChatAdminBinding.inflate(inflater, container, false)

        firebaseChat = FirebaseChat(messenger.idUser.toString())

        firebaseChat.getMess() { listMessDetail ->
            setMessDetailListRecycler(listMessDetail, userInfo)
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
    }

}