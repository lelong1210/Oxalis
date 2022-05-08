package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailSheetBookTourBinding
import com.example.oxalis.databinding.FragmentDetailTourInfoAdminBinding
import com.example.oxalis.service.FirebaseService

class DetailTourInfoAdminFragment : Fragment() {
    private var _binding: FragmentDetailTourInfoAdminBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTourInfoAdminBinding.inflate(inflater,container,false)


        // Inflate the layout for this fragment
        return binding.root
    }

}