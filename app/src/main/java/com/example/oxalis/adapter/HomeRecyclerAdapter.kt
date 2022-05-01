package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.databinding.HomeRecyclerRowItemBinding
import com.example.oxalis.model.AllCategory
import com.example.oxalis.model.TourInfo

class HomeRecyclerAdapter(val context: Context, private val allCategory: List<AllCategory>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    var onItemClick: ((TourInfo) -> Unit)? = null

    inner class HomeViewHolder(binding: HomeRecyclerRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var catTourInfo: TextView = binding.catAllTourInfo
        var itemRecycler: RecyclerView = binding.itemRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeRecyclerRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.catTourInfo.text = allCategory[position].categoryTitle
        setTourItemRecycler(holder.itemRecycler, allCategory[position].categoryItemList)
    }

    override fun getItemCount(): Int {
        return allCategory.size
    }

    private fun setTourItemRecycler(recyclerView: RecyclerView, tourInfoList: List<TourInfo>) {
        val itemRecyclerAdapter = TourItemAdapter(context, tourInfoList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
        itemRecyclerAdapter.onItemClickChild = { TourInfo ->
            onItemClick?.invoke(TourInfo)
        }
    }

}