package com.example.oxalis.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.databinding.ItemViewBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.login.LoginActivity

class TourItemAdapter(val context: Context, private val tourInfoList: List<TourInfo>) :
    RecyclerView.Adapter<TourItemAdapter.TourViewHolder>() {

    var onItemClickChild: ((TourInfo) -> Unit)? = null

    inner class TourViewHolder(binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var imageView: ImageView = binding.avatarOfTour
        var nameOfTour: TextView = binding.nameOfTour
        var rateOfTour = binding.rateOfTour
        var addressOfTour: TextView = binding.addressOfTour
        var priceOfTour: TextView = binding.priceOfTour

        init {

            binding.root.setOnClickListener {
                onItemClickChild?.invoke(tourInfoList[adapterPosition])
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.addressOfTour.text = tourInfoList[position].Address
        holder.imageView.setImageResource(tourInfoList[position].avatar!!.toInt())
        holder.nameOfTour.text = tourInfoList[position].name
        holder.rateOfTour.numStars = tourInfoList[position].rate!!
        holder.priceOfTour.text = tourInfoList[position].price
    }

    override fun getItemCount(): Int {
        return tourInfoList.size
    }
}