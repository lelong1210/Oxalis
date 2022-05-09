package com.example.oxalis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.databinding.HomeRecyclerRowItemTourInfoBinding
import com.example.oxalis.model.AllCategoryTourInfo
import com.example.oxalis.model.TourInfo

class HomeRecyclerTourInfoAdapter(val context: Context, private val allCategoryTourInfo: List<AllCategoryTourInfo>) :
    RecyclerView.Adapter<HomeRecyclerTourInfoAdapter.HomeViewHolder>() {

    var onItemClick: ((TourInfo) -> Unit)? = null

    inner class HomeViewHolder(binding: HomeRecyclerRowItemTourInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        var catTourInfo: TextView = binding.catAllTourInfo
        var itemRecycler: RecyclerView = binding.itemRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeRecyclerRowItemTourInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.catTourInfo.text = allCategoryTourInfo[position].categoryTitle
        setTourItemRecycler(holder.itemRecycler, allCategoryTourInfo[position].categoryItemList)
    }

    override fun getItemCount(): Int {
        return allCategoryTourInfo.size
    }

    private fun setTourItemRecycler(recyclerView: RecyclerView, tourInfoList: List<TourInfo>) {
        val itemRecyclerAdapter = TourInfoItemAdapter(context, 0,tourInfoList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
        itemRecyclerAdapter.onItemClickChild = {
            onItemClick?.invoke(it)
        }
    }

}