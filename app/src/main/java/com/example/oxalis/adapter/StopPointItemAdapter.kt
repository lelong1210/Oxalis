package com.example.oxalis.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.databinding.ItemViewDiscountBinding
import com.example.oxalis.databinding.ItemViewStopPointBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.TourInfo
import org.w3c.dom.Text

class StopPointItemAdapter(context: Context, private val stopPointInfoList:List<StopPointInfo>):RecyclerView.Adapter<StopPointItemAdapter.StopPointViewHolder>() {

    var onItemClickChild: ((StopPointInfo) -> Unit)? = null

    inner class StopPointViewHolder(binding: ItemViewStopPointBinding):RecyclerView.ViewHolder(binding.root){
        var avatarOfStopPointInfo: ImageView = binding.avatarOfStopPoint
        var timeOfStopPoint: TextView = binding.timeStartAndEndOfStopPoint
        var typeOfStopPoint: TextView = binding.typeOfServiceStopPoint
        var priceOfStopPoint: TextView = binding.priceOfTourStopPoint
        var addressOfStopPointInfo:TextView = binding.addressOfStopPoint

        init {
            binding.root.setOnClickListener {
                onItemClickChild?.invoke(stopPointInfoList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopPointViewHolder {
        val binding = ItemViewStopPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StopPointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StopPointViewHolder, position: Int) {
        holder.avatarOfStopPointInfo.setImageResource(R.drawable.facebook)
        holder.priceOfStopPoint.text = stopPointInfoList[position].price
        holder.timeOfStopPoint.text = stopPointInfoList[position].timeStart+" - "+stopPointInfoList[position].timeEnd
        holder.typeOfStopPoint.text = stopPointInfoList[position].typeOfService
        holder.addressOfStopPointInfo.text = stopPointInfoList[position].name_of_address

    }

    override fun getItemCount(): Int {
        return stopPointInfoList.size
    }


}

