package com.example.oxalis.view.childrenStatistical

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.oxalis.databinding.FragmentTopRatingBinding
import com.example.oxalis.service.FirebaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TopRatingFragment : Fragment() {

    private var _binding: FragmentTopRatingBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopRatingBinding.inflate(inflater, container, false)

        val column = AnyChart.column3d()
        val data: MutableList<DataEntry> = ArrayList()
        val anyChartViewColumn = binding.anyChartView

        firebaseService.getTourInfoOrDerBy("20") { tourInfoList ->
            data.clear()

            for (i in tourInfoList.indices) {
                if (tourInfoList[i].rate != "null" && tourInfoList[i].rate != null) {
                    data.add(ValueDataEntry(tourInfoList[i].id, tourInfoList[i].rate!!.toFloat()))
                    column.data(data)
                }
                if(data.size == 5){
                    break
                }
            }
            column.title("Tour được đánh giá tốt nhất")
            anyChartViewColumn.setChart(column)
        }
        binding.refreshBtn.setOnClickListener {
            firebaseService.getTourInfoOrDerBy("5") { tourInfoList ->
                data.clear()
                for (i in tourInfoList.indices) {
                    if (tourInfoList[i].rate != null) {
                        data.add(ValueDataEntry(tourInfoList[i].id, tourInfoList[i].rate!!.toFloat()))
                        column.data(data)
                    }
                }
                column.title("Tour được đánh giá tốt nhất")
                anyChartViewColumn.setChart(column)
            }
        }

        return binding.root
    }

}