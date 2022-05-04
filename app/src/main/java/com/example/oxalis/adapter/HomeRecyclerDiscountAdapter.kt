package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.databinding.HomeRecyclerRowItemDiscountBinding
import com.example.oxalis.databinding.HomeRecyclerRowItemTourInfoBinding
import com.example.oxalis.model.AllCategoryDiscount
import com.example.oxalis.model.AllCategoryTourInfo
import com.example.oxalis.model.Discount
import com.example.oxalis.model.TourInfo

class HomeRecyclerDiscountAdapter(val context: Context, private val allCategoryDiscount:List<AllCategoryDiscount>) :
    RecyclerView.Adapter<HomeRecyclerDiscountAdapter.HomeViewHolder>() {

    var onItemClick: ((Discount) -> Unit)? = null

    inner class HomeViewHolder(binding: HomeRecyclerRowItemDiscountBinding) : RecyclerView.ViewHolder(binding.root) {
        var catTourInfo: TextView = binding.catAllDiscount
        var itemRecycler: RecyclerView = binding.itemRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeRecyclerRowItemDiscountBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.catTourInfo.text = allCategoryDiscount[position].categoryTitle
        setTourItemRecycler(holder.itemRecycler, allCategoryDiscount[position].categoryItemList)
    }

    override fun getItemCount(): Int {
        return allCategoryDiscount.size
    }


    private fun setTourItemRecycler(recyclerView: RecyclerView, tourInfoList: List<Discount>) {
        val itemRecyclerAdapter = DiscountItemAdapter(context, tourInfoList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
        itemRecyclerAdapter.onItemClickChild={
            onItemClick?.invoke(it)
        }
    }
}