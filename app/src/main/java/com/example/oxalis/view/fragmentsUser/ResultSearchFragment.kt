package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.TourInfoItemAdapter
import com.example.oxalis.databinding.FragmentPreferentialBinding
import com.example.oxalis.databinding.FragmentResultSearchBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.details.DetailTourInfoAdminFragment

class ResultSearchFragment(private val listTour:List<TourInfo>) : Fragment() {

    private var _binding: FragmentResultSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var managerTourInfoRecyclerView:RecyclerView
    private lateinit var managerTourInfoAdapter:TourInfoItemAdapter
    var onItemClick:((TourInfo)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultSearchBinding.inflate(inflater, container, false)


        setManagerTourInfoListRecycler(listTour)


        return binding.root
    }
    private fun setManagerTourInfoListRecycler(listTourInfo: List<TourInfo>) {
        managerTourInfoRecyclerView = binding.listTourRecycler
        managerTourInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerTourInfoAdapter = TourInfoItemAdapter(requireContext(),1, listTourInfo)
        managerTourInfoRecyclerView.adapter = managerTourInfoAdapter
        managerTourInfoAdapter.onItemClickChild={
            onItemClick?.invoke(it)
        }
    }
}