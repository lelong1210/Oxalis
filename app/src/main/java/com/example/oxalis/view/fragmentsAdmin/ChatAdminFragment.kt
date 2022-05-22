package com.example.oxalis.view.fragmentsAdmin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.ChatAdminAdapter
import com.example.oxalis.adapter.TourInfoItemAdapter
import com.example.oxalis.databinding.FragmentAddUserBinding
import com.example.oxalis.databinding.FragmentChatAdminBinding
import com.example.oxalis.model.Messenger
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseChat
import com.example.oxalis.view.details.DetailChatAdminFragment
import com.example.oxalis.view.details.DetailTourInfoAdminFragment
import com.google.gson.Gson

class ChatAdminFragment() : Fragment() {

    private var _binding: FragmentChatAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var managerListChatRecyclerView: RecyclerView
    private lateinit var managerListChatAdapter: ChatAdminAdapter
    private lateinit var firebaseChat: FirebaseChat
    private var updateListChat = true
    var onClickRepeat: ((Boolean) -> Unit)? = null
    var onClickItemChatAdminFragment: ((Fragment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatAdminBinding.inflate(inflater, container, false)
        val userInfo = getUserInfo()
        firebaseChat = FirebaseChat(userInfo.id.toString())

        firebaseChat.getListMessAdmin {
            if (updateListChat) {
                setManagerListChatRecycler(it)
            }
        }
        return binding.root
    }

    private fun setManagerListChatRecycler(listChat: List<Messenger>) {
        val userInfo = getUserInfo()
        managerListChatRecyclerView = binding.listChatRecyclerView
        managerListChatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerListChatAdapter = ChatAdminAdapter(requireContext(), listChat)
        managerListChatRecyclerView.adapter = managerListChatAdapter
        managerListChatAdapter.onItemClick = {
            updateListChat = false
            val detailChatAdminFragment = DetailChatAdminFragment(it, userInfo)
            onClickItemChatAdminFragment?.invoke(detailChatAdminFragment)

            detailChatAdminFragment.onClickRepeat={status->
                if(status){
                    onClickRepeat?.invoke(true)
                }
            }
        }
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
/*
            detailChatAdminFragment.onClickRepeat = { status ->
                onClickRepeat?.invoke(status)
            }
            detailChatAdminFragment.updateListChat={ update->
                updateListChat = update
            }*/