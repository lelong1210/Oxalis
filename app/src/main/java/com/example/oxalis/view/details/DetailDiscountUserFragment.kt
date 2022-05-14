package com.example.oxalis.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailDiscountBinding
import com.example.oxalis.databinding.FragmentDetailDiscountUserBinding
import com.example.oxalis.model.Discount

class DetailDiscountUserFragment(val discount:Discount) : Fragment() {

    private var _binding: FragmentDetailDiscountUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailDiscountUserBinding.inflate(inflater,container,false)

        binding.discountCode.setText(discount.id)
        binding.percentDiscount.setText(discount.percentDiscount)

        return binding.root
    }
}