package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.databinding.HomeRecyclerRowItemDiscountBinding
import com.example.oxalis.databinding.HomeRecyclerRowItemStopPointBinding
import com.example.oxalis.databinding.HomeRecyclerRowItemTourInfoBinding
import com.example.oxalis.model.*

class HomeRecyclerStopPointAdapter(val context: Context, private val allCategoryStopPointInfo:List<AllCategoryStopPoint>) :
    RecyclerView.Adapter<HomeRecyclerStopPointAdapter.HomeViewHolder>() {

    var onItemClick: ((StopPointInfo) -> Unit)? = null

    inner class HomeViewHolder(binding: HomeRecyclerRowItemStopPointBinding) : RecyclerView.ViewHolder(binding.root) {
        var catStopPointInfo: TextView = binding.catAllStopPointInfo
        var itemRecycler: RecyclerView = binding.itemRecycler
    }



    private fun setTourItemRecycler(recyclerView: RecyclerView, stopPointList: List<StopPointInfo>) {
        val itemRecyclerAdapter = StopPointItemAdapter(context, stopPointList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
        itemRecyclerAdapter.onItemClickChild={
            onItemClick?.invoke(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeRecyclerRowItemStopPointBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.catStopPointInfo.text = allCategoryStopPointInfo[position].categoryTitle
        setTourItemRecycler(holder.itemRecycler, allCategoryStopPointInfo[position].categoryItemList)
    }

    override fun getItemCount(): Int {
        return allCategoryStopPointInfo.size
    }

}