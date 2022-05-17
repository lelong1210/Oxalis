package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.ItemViewDiscountBinding
import com.example.oxalis.databinding.ItemViewListChatAdminBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.model.Messenger
import com.example.oxalis.service.FirebaseService

class ChatAdminAdapter(val context: Context, private val listMessenger:List<Messenger>):RecyclerView.Adapter<ChatAdminAdapter.ChatAdminViewHolder>() {

    private val firebaseService = FirebaseService()
    var onItemClick: ((Messenger) -> Unit)? = null

    inner class ChatAdminViewHolder(binding:ItemViewListChatAdminBinding):RecyclerView.ViewHolder(binding.root){
        val imageProfile:ImageView = binding.profileImage
        val nameOfUserSend:TextView = binding.nameOfUserSend
        val contentLastMess:TextView = binding.contentLastMess
        val timeLastMess:TextView = binding.timeLastSend
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMessenger[adapterPosition])
            }
        }
        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(imageProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdminViewHolder {
        val binding =
            ItemViewListChatAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatAdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatAdminViewHolder, position: Int) {
        holder.contentLastMess.text = listMessenger[position].messLastSend
        holder.nameOfUserSend.text = listMessenger[position].nameOfUser
        holder.timeLastMess.text = listMessenger[position].timeLastSend
        firebaseService.getOnlyImageUser("${listMessenger[position].idUser}avatar"){
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return listMessenger.size
    }
}