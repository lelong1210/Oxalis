package com.example.oxalis.view.fragmentsUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailTourInfoBinding
import com.example.oxalis.databinding.FragmentPreferentialBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.arrayOfImage
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat


class DetailTourInfoFragment(val tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentDetailTourInfoBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    var btnBookTourClickChild: ((TourInfo) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTourInfoBinding.inflate(inflater, container, false)


//        val tourInfo = intent.getSerializableExtra("tourInfo") as TourInfo

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
            btnBookTourClickChild?.invoke(tourInfo)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}