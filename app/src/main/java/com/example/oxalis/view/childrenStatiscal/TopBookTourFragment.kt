package com.example.oxalis.view.childrenStatiscal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentProceedBinding
import com.example.oxalis.databinding.FragmentTopBookTourBinding
import com.example.oxalis.service.FirebaseService

class TopBookTourFragment : Fragment() {

    private var _binding: FragmentTopBookTourBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopBookTourBinding.inflate(inflater, container, false)

        val column = AnyChart.pie3d()
        val data: MutableList<DataEntry> = ArrayList()
        val anyChartViewColumn = binding.anyChartViewColumn

        firebaseService.getAllTourInfo { tourInfoList ->
            for (i in tourInfoList.indices) {
                data.clear()
                firebaseService.getBookTourSheetWhere(
                    "idTour",
                    "${tourInfoList[i].id}"
                ) { listBookSheet ->
                    if (listBookSheet.isNotEmpty()) {
                        data.add(ValueDataEntry(listBookSheet[0].nameOfTour, listBookSheet.size))
                        column.data(data)
                    }

                }
                if (i == tourInfoList.size - 1) {
                    anyChartViewColumn.setChart(column)
                    column.title("Tour được đặt nhiều nhất")
                }
            }
        }

        binding.refreshBtn.setOnClickListener {
            firebaseService.getAllTourInfo { tourInfoList ->
                for (i in tourInfoList.indices) {
                    data.clear()
                    firebaseService.getBookTourSheetWhere(
                        "idTour",
                        "${tourInfoList[i].id}"
                    ) { listBookSheet ->
                        if (listBookSheet.isNotEmpty()) {
                            data.add(ValueDataEntry(listBookSheet[0].nameOfTour, listBookSheet.size))
                            column.data(data)
                        }

                    }
                    if (i == tourInfoList.size - 1) {
                        anyChartViewColumn.setChart(column)
                        column.title("Tour được đặt nhiều nhất")
                    }
                }
            }
        }

        return binding.root
    }
}