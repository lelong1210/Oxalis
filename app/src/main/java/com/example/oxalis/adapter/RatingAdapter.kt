package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.ItemCommentRatingBinding
import com.example.oxalis.model.RatingTour
import com.example.oxalis.model.ReplyRatingTour
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService

class RatingAdapter(private val context: Context, private val listComment: List<RatingTour>,private val isReply:Boolean):
    RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {

    var onItemClick:((RatingTour)->Unit)?=null
    private lateinit var listReplyCommentRecyclerView: RecyclerView
    private lateinit var ratingReplyAdapter: ReplyAdapter

    private val firebaseService = FirebaseService()

        inner class RatingViewHolder(val binding:ItemCommentRatingBinding):RecyclerView.ViewHolder(binding.root){
            val avatar = binding.commentAvatar
            val comment = binding.commentContent
            val rate = binding.rateOfTour
            val name = binding.commentName

            fun bind(uri: Uri) {
                Glide.with(context).load(uri).into(avatar)
            }
            init {
                binding.root.setOnClickListener {
                    onItemClick?.invoke(listComment[adapterPosition])
                }
            }
           fun setManagerReplyRatingListRecycler(listReplyComment: List<ReplyRatingTour>) {
                listReplyCommentRecyclerView = binding.listReplyCommentRecycler
                listReplyCommentRecyclerView.layoutManager = LinearLayoutManager(context)
                ratingReplyAdapter = ReplyAdapter(context, listReplyComment)
                listReplyCommentRecyclerView.adapter = ratingReplyAdapter
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val binding = ItemCommentRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.name.text = listComment[position].nameUser
        holder.comment.text = listComment[position].content
        holder.rate.rating = listComment[position].rate!!.toFloat()
        firebaseService.getOnlyImageUser("${listComment[position].idUser}avatar"){
            holder.bind(it)
        }
        if(isReply){
            firebaseService.getRelyRatingTourWhere(listComment[position].id.toString()){list->
                holder.setManagerReplyRatingListRecycler(list)
            }
        }
    }

    override fun getItemCount(): Int {
        return listComment.size
    }



}