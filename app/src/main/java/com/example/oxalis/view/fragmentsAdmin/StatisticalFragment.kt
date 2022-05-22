package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.oxalis.R
import com.example.oxalis.adapter.TabLayOutAdapter
import com.example.oxalis.databinding.FragmentStatisticalBinding
import com.example.oxalis.model.District
import com.example.oxalis.model.Province
import com.example.oxalis.model.Ward
import com.example.oxalis.model.arrayGender
import com.example.oxalis.service.FirebaseAddress
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.childrenStatiscal.ProceedFragment
import com.example.oxalis.view.childrenStatiscal.TopBookTourFragment
import com.example.oxalis.view.childrenStatiscal.TopRatingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class StatisticalFragment : Fragment() {

    private var _binding: FragmentStatisticalBinding? = null
    private val binding get() = _binding!!
    private val tabTitle = arrayListOf("Top chất lượng nhất","Top đặt nhiều nhất" ,"Tổng doanh thu")
    private val topRatingOfTourFragment = TopRatingFragment()
    private val topBookTourFragment = TopBookTourFragment()
    private val proceedFragment = ProceedFragment()
    private val listTabFrameLayout = arrayListOf(topRatingOfTourFragment,topBookTourFragment,proceedFragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStatisticalBinding.inflate(inflater, container, false)

        val viewPager2: ViewPager2 = binding.viewPagerPurchase
        val tabLayOut: TabLayout = binding.tabLayoutPurchase
        val tabLayOutAdapter = TabLayOutAdapter(
            activity?.supportFragmentManager!!,
            activity?.lifecycle!!,
            listTabFrameLayout
        )
        viewPager2.adapter = tabLayOutAdapter

        TabLayoutMediator(tabLayOut, viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()


        return binding.root
    }
}
/*        val firebaseAddress = FirebaseAddress()
        firebaseAddress.getAddress { list ->
            listProvince = list as ArrayList<Province>
            itemsSex.clear()
            for (i in list.indices) {
                itemsSex.add("${list[i].name}")
            }
            val adapterSex =
                ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsSex)
            binding.slbSex.setAdapter(adapterSex)
        }

        binding.slbSex1.setOnClickListener {
            val province = binding.slbSex.text.toString()
            if (province != "") {
                listDistrict = listProvince[itemsSex.indexOf(province)].list!!
                itemsSex1.clear()
                for (i in 0 until listDistrict.size) {
                    itemsSex1.add("${listDistrict!![i].name}")
                }
                val adapterSex1 =
                    ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsSex1)
                binding.slbSex1.setAdapter(adapterSex1)
            }
        }
        binding.slbSex2.setOnClickListener {
            val district = binding.slbSex1.text.toString()
            if (district != "") {
                listWard = listDistrict[itemsSex1.indexOf(district)].list!!

                itemsSex2.clear()
                for (i in 0 until listWard.size) {
                    itemsSex2.add("${listWard!![i].name}")
                }
                val adapterSex2 =
                    ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsSex2)
                binding.slbSex2.setAdapter(adapterSex2)
            }
        }
            private var listProvince = ArrayList<Province>()
    private var listDistrict = ArrayList<District>()
    private var listWard = ArrayList<Ward>()
    var itemsSex = ArrayList<String>()
    var itemsSex1 = ArrayList<String>()
    var itemsSex2 = ArrayList<String>()





---------------------------------------------------------


        val column = AnyChart.pie()
        val data: MutableList<DataEntry> = ArrayList()
        val anyChartViewColumn = binding.anyChartViewColumn
        firebaseService.getAllTourInfo {
//                tourInfoList ->
//            for (i in tourInfoList.indices) {
//                firebaseService.getBookTourSheetWhere("idTour", "${tourInfoList[i].id}") { listBookSheet ->
//                    if(listBookSheet.isNotEmpty()){
//                        data.add(ValueDataEntry(listBookSheet[0].nameOfTour, listBookSheet.size))
//                        column.data(data)
//                    }
//                }
//                if(i == tourInfoList.size-1){
//                    column.title("Tour được đặt nhiều nhất")
//                    anyChartViewColumn.setChart(column)
//                }
//            }
        }
        firebaseService.getTourInfoOrDerBy("12") { tourInfoList ->
            for (i in tourInfoList.indices) {
                if (tourInfoList[i].rate != null) {
                    data.add(ValueDataEntry(tourInfoList[i].id, tourInfoList[i].rate!!.toFloat()))
                    column.data(data)
                }
            }
            column.title("Tour được đặt nhiều nhất")
            anyChartViewColumn.setChart(column)
        }

        */