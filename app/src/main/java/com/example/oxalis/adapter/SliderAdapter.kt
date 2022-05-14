package com.example.oxalis.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.SliderItemBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService

class SliderAdapter(private val context: Context,private val viewPager2: ViewPager2, private val listTour: ArrayList<TourInfo>):RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private val firebaseService = FirebaseService()

    inner class SliderViewHolder(private val binding: SliderItemBinding):RecyclerView.ViewHolder(binding.root){
        val img: ImageView = binding.imageSlider
        val nameOfTour = binding.nameOfTour
        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding =
            SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        firebaseService.getOnlyImage(listTour[position].id.toString()){
            holder.bind(it)
        }
        holder.nameOfTour.text = listTour[position].name

        if(position == listTour.size -2){
            viewPager2.post(run)
        }
    }

    override fun getItemCount(): Int {
        return listTour.size
    }
    private val run = Runnable {
        listTour.addAll(listTour)
        notifyDataSetChanged()
    }
}

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_item,parent,false))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val listImg = imgList[position]
        holder.img.setImageResource(listImg.img)
        if(position == imgList.size -2){
            viewPager2.post(run)
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }
    private val run = Runnable {
        imgList.addAll(imgList)
        notifyDataSetChanged()
    }*/