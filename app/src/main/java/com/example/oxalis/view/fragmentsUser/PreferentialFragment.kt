package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentAccountBinding
import com.example.oxalis.databinding.FragmentPreferentialBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService

class PreferentialFragment : Fragment() {

    private var _binding: FragmentPreferentialBinding? = null
    private val binding get() = _binding!!

    var onItemClick:((List<StopPointInfo>)->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreferentialBinding.inflate(inflater, container, false)



        binding.intoInsertStopPoint.setOnClickListener {
            val firebaseService = FirebaseService()
//            firebaseService.getStopPoint("sp0"){
//                onItemClick?.invoke(it)
//            }
            firebaseService.getAllStopPoint {
                onItemClick?.invoke(it)
            }

        }

        return binding.root
    }

}