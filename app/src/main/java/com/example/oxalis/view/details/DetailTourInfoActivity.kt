package com.example.oxalis.view.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityDetailTourInfoBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.arrayOfImage
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailTourInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTourInfoBinding
    private val firebaseService = FirebaseService()
    var btnBackClick: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tourInfo = intent.getSerializableExtra("tourInfo") as TourInfo

        binding.nameOfTour.text = tourInfo.name
        binding.timeStartOfTour.text = tourInfo.timeStart
        binding.rateOfTour.text
        binding.textView14.text = tourInfo.describe

        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = tourInfo.price!!.toDouble()
        val formattedNumber = formatter.format(myNumber)

        binding.priceOfTour.text = "đ $formattedNumber"

        if (tourInfo.rate == null) {
            binding.rateOfTour.text = "Chưa có đánh giá"
        } else {
            binding.rateOfTour.text = tourInfo.rate
        }

        firebaseService.getUrlImage(tourInfo.id!!, arrayOfImage) { arrayUri ->
            Glide.with(this).load(arrayUri[0]).into(binding.avatarOfTourMain)
            Glide.with(this).load(arrayUri[1]).into(binding.avatarOfTour1st)
            Glide.with(this).load(arrayUri[2]).into(binding.avatarOfTour2st)
            Glide.with(this).load(arrayUri[3]).into(binding.avatarOfTour3st)
        }

        binding.btnBookTour.setOnClickListener {
            Log.i("test","book tour")
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}