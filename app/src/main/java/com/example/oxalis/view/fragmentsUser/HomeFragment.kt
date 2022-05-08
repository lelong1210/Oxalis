package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.HomeRecyclerDiscountAdapter
import com.example.oxalis.adapter.HomeRecyclerStopPointAdapter
import com.example.oxalis.adapter.HomeRecyclerTourInfoAdapter
import com.example.oxalis.databinding.FragmentHomeBinding
import com.example.oxalis.model.*
import com.example.oxalis.service.FirebaseService

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeCategoryTourInfoRecycler: RecyclerView
    private lateinit var homeRecyclerTourInfoAdapter: HomeRecyclerTourInfoAdapter

    private lateinit var homeCategoryDiscountRecycler: RecyclerView
    private lateinit var homeRecyclerDiscountAdapter: HomeRecyclerDiscountAdapter


    private val firebaseService = FirebaseService()

    var onItemTourInfoClick: ((TourInfo) -> Unit)? = null
    var onItemDiscountClick: ((Discount) -> Unit)? = null
    private lateinit var tourInfo: TourInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // tour Info
        val allCategoryTourInfo: MutableList<AllCategoryTourInfo> = ArrayList()
        firebaseService.getAllTourInfo { arrayListStopPointInfo ->
            allCategoryTourInfo.add(AllCategoryTourInfo("Tour", arrayListStopPointInfo))
            setHomeCategoryTourInfoRecycler(allCategoryTourInfo)
        }

        return binding.root
    }

    private fun setHomeCategoryTourInfoRecycler(allCategory: List<AllCategoryTourInfo>) {
        homeCategoryTourInfoRecycler = binding.homeRecyclerViewTourInfo
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        homeCategoryTourInfoRecycler.layoutManager = layoutManager
        homeRecyclerTourInfoAdapter = HomeRecyclerTourInfoAdapter(requireContext(), allCategory)
        homeCategoryTourInfoRecycler.adapter = homeRecyclerTourInfoAdapter
        homeRecyclerTourInfoAdapter.onItemClick = {
            onItemTourInfoClick?.invoke(it)
        }
    }

    private fun setHomeCategoryDiscountRecycler(allCategory: List<AllCategoryDiscount>) {
        homeCategoryDiscountRecycler = binding.homeRecyclerViewDiscount
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        homeCategoryDiscountRecycler.layoutManager = layoutManager
        homeRecyclerDiscountAdapter = HomeRecyclerDiscountAdapter(requireContext(), allCategory)
        homeCategoryDiscountRecycler.adapter = homeRecyclerDiscountAdapter
        homeRecyclerDiscountAdapter.onItemClick = {
            onItemDiscountClick?.invoke(it)
        }
    }

}
/*        // StopPoint
        val allCategoryStopPointInfo: MutableList<AllCategoryStopPoint> = ArrayList()
        firebaseService.getAllStopPoint {
            stopPointInfoList ->
            allCategoryStopPointInfo.add(AllCategoryStopPoint("StopPoint",stopPointInfoList))
            setHomeCategoryStopPointRecycler(allCategoryStopPointInfo)

              // Discount
        val tourItemList2: MutableList<Discount> = ArrayList()
        tourItemList2.add(Discount(R.drawable.frutorials, 12))
        tourItemList2.add(Discount(R.drawable.facebook, 12))
        tourItemList2.add(Discount(R.drawable.images_discount, 12))
        tourItemList2.add(Discount(R.drawable.facebook, 12))
        tourItemList2.add(Discount(R.drawable.frutorials, 12))


        val allCategoryDiscount: MutableList<AllCategoryDiscount> = ArrayList()
        allCategoryDiscount.add(AllCategoryDiscount("DANH SACH DISCOUNT", tourItemList2))
        setHomeCategoryDiscountRecycler(allCategoryDiscount)

        }*/