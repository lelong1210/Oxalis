package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.ItemViewBookTourBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.service.FirebaseService

class BookTourAdapter(
    val context: Context,
    private val listBookTour: List<SheetAddInformationCart>
) :
    RecyclerView.Adapter<BookTourAdapter.BookItemViewHolder>() {

    private val firebaseService = FirebaseService()
    var onClickItem: ((SheetAddInformationCart) -> Unit)? = null

    inner class BookItemViewHolder(binding: ItemViewBookTourBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var imageOfTour: ImageView = binding.tourImageView
        var nameOfTour: TextView = binding.nameOfTour
        var nameOfUser: TextView = binding.nameOfUser

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(imageOfTour)
        }

        init {
            binding.root.setOnClickListener {
                onClickItem?.invoke(listBookTour[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val binding =
            ItemViewBookTourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        firebaseService.getOnlyImage(listBookTour[position].idTour.toString()) {
            holder.nameOfTour.text = listBookTour[position].nameOfTour
            holder.nameOfUser.text = listBookTour[position].nameOfUserBookTour
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return listBookTour.size
    }
}