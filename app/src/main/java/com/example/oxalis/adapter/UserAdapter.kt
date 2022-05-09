package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.ItemViewUserBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService

class UserAdapter(private val context: Context, private val accountList: List<UserInfo>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val firebaseService = FirebaseService()
    var onItemClickChild: ((UserInfo) -> Unit)? = null

    inner class UserViewHolder(binding: ItemViewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imageViewAccount: ImageView = binding.accountImageView
        var nameOfUser: TextView = binding.nameOfUser
        var permissionOfUser: TextView = binding.permission

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(imageViewAccount)
        }

        init {
            binding.root.setOnClickListener {
                onItemClickChild?.invoke(accountList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemViewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.nameOfUser.text = accountList[position].fullname
        holder.permissionOfUser.text = accountList[position].permission
        firebaseService.getOnlyImageUser(accountList[position].avatar.toString()) {
            holder.bind(it)
        }
    }
    override fun getItemCount(): Int {
        return accountList.size
    }

}

