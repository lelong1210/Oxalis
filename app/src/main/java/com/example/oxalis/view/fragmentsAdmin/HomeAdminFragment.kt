package com.example.oxalis.view.fragmentsAdmin

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentHomeAdminBinding
import com.example.oxalis.databinding.FragmentInsertStopPointBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import java.util.ArrayList


class HomeAdminFragment : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    var onCardViewClick: ((Fragment) -> Unit)? = null
    private val insertTourFragment = InsertTourFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)

        binding.cardViewStatistical.setOnClickListener {
            Log.i("test","cardViewStatistical")
        }
        binding.cardViewManagerTour.setOnClickListener {
            onCardViewClick?.invoke(insertTourFragment)
        }
        binding.cardViewManagerBookTour.setOnClickListener {
            Log.i("test","cardViewManagerBookTour")
        }
        binding.cardViewManagerCustomer.setOnClickListener {
            Log.i("test","cardViewManagerCustomer")
        }
        binding.cardViewManagerDiscount.setOnClickListener {
            Log.i("test","cardViewManagerDiscount")
        }
        binding.cardViewManagerAccount.setOnClickListener {
            Log.i("test","cardViewManagerAccount")
        }


        return binding.root
    }

}
