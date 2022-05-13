package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.databinding.FragmentChatBinding
import com.example.oxalis.databinding.ItemReceiveMessageBinding
import com.example.oxalis.databinding.ItemSendMessageBinding
import com.example.oxalis.databinding.ItemViewTourInfoBinding
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.UserInfo

class ChatAdapter(val context: Context, private val listChat: List<MessengerDetail>, val userInfo: UserInfo):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SendViewHolder(binding: ItemSendMessageBinding):RecyclerView.ViewHolder(binding.root){
        var contentMess = binding.textMessageBody
        var time = binding.timeSend
    }

    inner class ReceiveViewHolder(binding: ItemReceiveMessageBinding):RecyclerView.ViewHolder(binding.root){
        var contentMess = binding.textMessageBody
        var time = binding.timeSend
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding =
                    ItemSendMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SendViewHolder(binding)
            }
            1 -> {
                val binding =
                    ItemReceiveMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ReceiveViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemReceiveMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ReceiveViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == 0){
            (holder as SendViewHolder).contentMess.text = listChat[position].content
            holder.time.text = listChat[position].timeSend
        }else if(getItemViewType(position) == 1){
            (holder as ReceiveViewHolder).contentMess.text = listChat[position].content
            holder.time.text = listChat[position].timeSend
        }
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(listChat[position].idUserSend == userInfo.id){
            0
        }else{
            1
        }
    }
}