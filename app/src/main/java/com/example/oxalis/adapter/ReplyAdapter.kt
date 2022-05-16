package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.ItemReplyCommentBinding
import com.example.oxalis.model.ReplyRatingTour
import com.example.oxalis.service.FirebaseService

class ReplyAdapter(private val context: Context, private val listReply: List<ReplyRatingTour>) :
    RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>() {

    private val firebaseService = FirebaseService()

    inner class ReplyViewHolder(binding: ItemReplyCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val content = binding.commentContent
        val avatar = binding.commentReplyAvatar
        val name = binding.commentReplyName

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(avatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding =
            ItemReplyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.content.text = listReply[position].content
        holder.name.text = listReply[position].nameOfAdmin
        firebaseService.getOnlyImageUser("${listReply[position].idAdmin}avatar"){
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return listReply.size
    }
}