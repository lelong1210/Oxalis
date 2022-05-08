package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.databinding.ItemViewDiscountBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.model.TourInfo

class DiscountItemAdapter(context: Context, private val discountList:List<Discount>):RecyclerView.Adapter<DiscountItemAdapter.DiscountViewHolder>() {

    var onItemClickChild: ((Discount) -> Unit)? = null

    inner class DiscountViewHolder(binding: ItemViewDiscountBinding):RecyclerView.ViewHolder(binding.root){
        var percent: TextView = binding.discountPercentTour
        var numberAvailability: TextView = binding.numberAvailability
        init {
            binding.root.setOnClickListener {
                onItemClickChild?.invoke(discountList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountViewHolder {
        val binding =
            ItemViewDiscountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        holder.numberAvailability.text = "sô phiếu còn lại ${discountList[position].numberAvailability}"
        holder.percent.text = "được giảm ${discountList[position].percentDiscount}%"
    }

    override fun getItemCount(): Int {
        return discountList.size
    }
}

