package com.example.oxalis.view.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityDetailTourInfoBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.arrayOfImage
import com.example.oxalis.service.FirebaseService

class DetailTourInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTourInfoBinding
    private val firebaseService = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stopPointInfo = intent.getSerializableExtra("stopPointInfo") as StopPointInfo

        binding.nameOfTour.text = stopPointInfo.name_of_address
        binding.timeStartOfTour.text = stopPointInfo.timeStart
        binding.rateOfTour.text = stopPointInfo.contract


        firebaseService.getUrlImage(stopPointInfo.id!!, arrayOfImage) { arrayUri ->
            Glide.with(this).load(arrayUri[0]).into(binding.avatarOfTourMain)
            Glide.with(this).load(arrayUri[1]).into(binding.avatarOfTour1st)
            Glide.with(this).load(arrayUri[2]).into(binding.avatarOfTour2st)
            Glide.with(this).load(arrayUri[3]).into(binding.avatarOfTour3st)
        }
    }
}