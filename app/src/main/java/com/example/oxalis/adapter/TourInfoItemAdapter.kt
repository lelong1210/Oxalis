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
import com.example.oxalis.databinding.ItemViewTourInfoBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class TourInfoItemAdapter(val context: Context, private val tourInfoList: List<TourInfo>) :
    RecyclerView.Adapter<TourInfoItemAdapter.TourViewHolder>() {

    var onItemClickChild: ((TourInfo) -> Unit)? = null
    private val firebaseService = FirebaseService()

    inner class TourViewHolder(binding: ItemViewTourInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var imageView: ImageView = binding.avatarOfTour
        var nameOfTour: TextView = binding.nameOfTour
        var rateOfTour = binding.rateOfTour
        var addressOfTour: TextView = binding.addressOfTour
        var priceOfTour: TextView = binding.priceOfTour
        var statusRate:TextView = binding.statusRate
        var timeOfTour:TextView = binding.dateItem

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(imageView)
        }

        init {

            binding.root.setOnClickListener {
                onItemClickChild?.invoke(tourInfoList[adapterPosition])
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val binding =
            ItemViewTourInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {

        firebaseService.getOnlyImage(tourInfoList[position].id!!) {
            holder.bind(it)
            holder.addressOfTour.text = tourInfoList[position].adrress
            holder.nameOfTour.text = tourInfoList[position].name
            holder.timeOfTour.text = tourInfoList[position].timeStart

            val formatter: NumberFormat = DecimalFormat("#,###")
            val myNumber = tourInfoList[position].price!!.toDouble()
            val formattedNumber = formatter.format(myNumber)

            holder.priceOfTour.text = "đ $formattedNumber"

            if (tourInfoList[position].rate == null) {
                holder.rateOfTour.rating = 0.1f
                holder.statusRate.text = "Chưa có đánh giá"
            } else {
                holder.rateOfTour.rating = tourInfoList[position].rate!!.toFloat()
            }

        }

    }

    override fun getItemCount(): Int {
        return tourInfoList.size
    }
}