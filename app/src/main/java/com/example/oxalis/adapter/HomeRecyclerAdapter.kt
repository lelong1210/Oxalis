package com.example.oxalis.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.model.AllCategory
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.fragments.PurchaseOderFragment

class HomeRecyclerAdapter(val context: Context, private val allCategory: List<AllCategory>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    private lateinit var tourInfoItem: TourInfo
    var onItemClick:((TourInfo)->Unit)? = null

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catTourInfo: TextView = itemView.findViewById(R.id.cat_all_tour_info)
        var itemRecycler: RecyclerView = itemView.findViewById(R.id.item_recycler)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(context).inflate(R.layout.home_recycler_row_item, parent, false)
        )
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
            tourInfoItem = TourInfo(
                TourInfo.name,
                TourInfo.price,
                TourInfo.avatar,
                TourInfo.rate,
                TourInfo.Address
            )
            onItemClick?.invoke(tourInfoItem!!)
        }
    }

}