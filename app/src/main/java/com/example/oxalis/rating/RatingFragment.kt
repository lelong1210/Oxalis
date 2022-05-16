package com.example.oxalis.rating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentConfirmBookTourBinding
import com.example.oxalis.databinding.FragmentRatingBinding
import com.example.oxalis.model.RatingTour
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.arrayDisplay
import com.example.oxalis.service.FirebaseService
import java.text.SimpleDateFormat
import java.util.*


class RatingFragment(val sheetAddInformationCart: SheetAddInformationCart,val tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    var onItemRate:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRatingBinding.inflate(inflater, container, false)

        firebaseService.getRating("RatingTour${sheetAddInformationCart.idUser}${tourInfo.id}"){
            rating ->
            binding.tourRatingStarSelect.rating = rating.rate!!.toFloat()
            binding.editTourRatingContent.setText(rating.content)
        }

        binding.sendReviewBtn.setOnClickListener {
            val currentTime = getCurrentTime()

            firebaseService.getLastId("Rating","lastId"){lastId ->
                val id = "RatingTour${sheetAddInformationCart.idUser}${sheetAddInformationCart.idTour}"
                val content = binding.editTourRatingContent.text
                val rate = binding.tourRatingStarSelect.rating

                val ratingTour = RatingTour(
                    "$id",
                    "${sheetAddInformationCart.idTour}",
                    "${sheetAddInformationCart.idUser}",
                    "${sheetAddInformationCart.nameOfUserBookTour}",
                    "$content",
                    "$rate",
                    "$currentTime",
                    arrayDisplay[1],
                )
                firebaseService.insertRatingTour(ratingTour){
                    if(it){
                        Toast.makeText(requireContext(),"Cảm ơn đánh giá của quý khách",Toast.LENGTH_SHORT).show()
                        onItemRate?.invoke(true)
                    }
                }
            }
        }

        return binding.root
    }

    private fun getCurrentTime(): String {
        val myFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return myFormat.format(Date())
    }
}