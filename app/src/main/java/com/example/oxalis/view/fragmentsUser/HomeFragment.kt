package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.oxalis.R
import com.example.oxalis.adapter.HomeRecyclerDiscountAdapter
import com.example.oxalis.adapter.HomeRecyclerStopPointAdapter
import com.example.oxalis.adapter.HomeRecyclerTourInfoAdapter
import com.example.oxalis.adapter.SliderAdapter
import com.example.oxalis.databinding.FragmentHomeBinding
import com.example.oxalis.model.*
import com.example.oxalis.service.FirebaseService
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeCategoryTourInfoRecycler: RecyclerView
    private lateinit var homeRecyclerTourInfoAdapter: HomeRecyclerTourInfoAdapter


    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var sliderHandler: Handler
    private lateinit var sliderRun: Runnable

    private val firebaseService = FirebaseService()

    var onItemTourInfoClick: ((TourInfo) -> Unit)? = null
    var onItemDiscountClick: ((Discount) -> Unit)? = null
    var onItemSearchClick: ((List<TourInfo>) -> Unit)? = null
    private lateinit var tourInfo: TourInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // tour Info
        val allCategoryTourInfo: MutableList<AllCategoryTourInfo> = ArrayList()
        firebaseService.getTourWhereHomLimit("PHONG NHA - KẺ BÀNG", listOf("")) { arrayTourInfo1 ->
            allCategoryTourInfo.add(AllCategoryTourInfo("PHONG NHA - KẺ BÀNG", arrayTourInfo1))
            firebaseService.getTourWhereHomLimit("QUẢNG BÌNH", listOf("")) { arrayTourInfo2 ->
                allCategoryTourInfo.add(AllCategoryTourInfo("QUẢNG BÌNH", arrayTourInfo2))
                setHomeCategoryTourInfoRecycler(allCategoryTourInfo)
            }
        }
        // search
        binding.btnSearch.setOnClickListener {
            val value = binding.searchTour.text.toString()
            if (value != "") {
                firebaseService.getTourWhere(
                    value.uppercase(),
                    listOf("adrress", "name", "price")
                ) {
                    onItemSearchClick?.invoke(it)
                }
            }

        }
        // tour discount 10%
        firebaseService.getTourWhereSlider("10") {
            itemSliderView(it)
        }

        return binding.root
    }

    private fun itemSliderView(listTourInfo: ArrayList<TourInfo>) {

        val viewPagerImgSlider: ViewPager2 = binding.viewPagerImgSlider
        sliderAdapter = SliderAdapter(requireContext(), viewPagerImgSlider, listTourInfo)
        viewPagerImgSlider.adapter = sliderAdapter
        viewPagerImgSlider.clipToPadding = false
        viewPagerImgSlider.clipChildren = false
        viewPagerImgSlider.offscreenPageLimit = 3
        viewPagerImgSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val comPosPageTarn = CompositePageTransformer()
        comPosPageTarn.addTransformer(MarginPageTransformer(40))
        comPosPageTarn.addTransformer { page, position ->
            var r: Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPagerImgSlider.setPageTransformer(comPosPageTarn)
        sliderHandler = Handler()
        sliderRun = Runnable {
            viewPagerImgSlider.currentItem = viewPagerImgSlider.currentItem + 1
        }
        viewPagerImgSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRun)
                    sliderHandler.postDelayed(sliderRun, 2000)
                }
            }
        )

        sliderAdapter.onItemClick={
            onItemTourInfoClick?.invoke(it)
        }


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
        homeRecyclerTourInfoAdapter.onItemClickViewAll={value->
            firebaseService.getTourWhere(
                value.uppercase(),
                listOf("adrress", "name", "price")
            ) {
                onItemSearchClick?.invoke(it)
            }
        }
    }
}
