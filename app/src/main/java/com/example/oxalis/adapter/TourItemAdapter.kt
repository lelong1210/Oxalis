package com.example.oxalis.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.login.LoginActivity

class TourItemAdapter(val context: Context, private val tourInfoList: List<TourInfo>) :
    RecyclerView.Adapter<TourItemAdapter.TourViewHolder>() {

    var onItemClickChild:((TourInfo)->Unit)? = null

    inner class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.avatar_of_tour)
        var nameOfTour: TextView = itemView.findViewById(R.id.name_of_tour)
        var rateOfTour: TextView = itemView.findViewById(R.id.rate_of_tour)
        var addressOfTour: TextView = itemView.findViewById(R.id.address_of_tour)
        var priceOfTour: TextView = itemView.findViewById(R.id.price_of_tour)

        init {
            itemView.setOnClickListener {
                onItemClickChild?.invoke(tourInfoList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        return TourViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {

        holder.addressOfTour.text = tourInfoList[position].Address
        holder.imageView.setImageResource(R.drawable.frutorials)
        holder.nameOfTour.text = tourInfoList[position].name
        holder.rateOfTour.text = tourInfoList[position].rate.toString()
        holder.priceOfTour.text = tourInfoList[position].price
    }

    override fun getItemCount(): Int {
        return tourInfoList.size
    }
}