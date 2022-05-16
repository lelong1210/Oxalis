package com.example.oxalis.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentConfirmBookTourBinding
import com.example.oxalis.databinding.FragmentRatingBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo


class RatingFragment(sheetAddInformationCart: SheetAddInformationCart,tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRatingBinding.inflate(inflater, container, false)



        return binding.root
    }


}