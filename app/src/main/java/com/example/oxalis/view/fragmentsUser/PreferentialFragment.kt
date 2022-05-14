package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.DiscountItemAdapter
import com.example.oxalis.databinding.FragmentAccountBinding
import com.example.oxalis.databinding.FragmentPreferentialBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailDiscountFragment

class PreferentialFragment : Fragment() {

    private var _binding: FragmentPreferentialBinding? = null
    private val binding get() = _binding!!
    private lateinit var managerDiscountRecyclerView:RecyclerView
    private lateinit var managerDiscountAdapter:DiscountItemAdapter
    var onItemClick:((Discount)->Unit)? = null
    private var firebaseService = FirebaseService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreferentialBinding.inflate(inflater, container, false)

        firebaseService.getAllDiscount {
            setManagerDiscountListRecycler(it)
        }

        return binding.root
    }
    private fun setManagerDiscountListRecycler(listDiscount: List<Discount>) {
        managerDiscountRecyclerView = binding.listDiscountRecycler
        managerDiscountRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerDiscountAdapter = DiscountItemAdapter(requireContext(), listDiscount)
        managerDiscountRecyclerView.adapter = managerDiscountAdapter
        managerDiscountAdapter.onItemClickChild={
          onItemClick?.invoke(it)
        }
    }

}