package com.example.oxalis.view.childrenStatistical

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.databinding.FragmentProceedBinding
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat

class ProceedFragment : Fragment() {

    private var _binding: FragmentProceedBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProceedBinding.inflate(inflater, container, false)
        var totalProcess = 0.0
        firebaseService.getBookTourSheetWhere("status", arrayOfStatusSheet[1]) { listSheet ->
            for(i in listSheet.indices){
                if (listSheet[i].discountCode != "") {
                    firebaseService.getDiscount(listSheet[i].discountCode.toString()) {
                        if (it.id == "" || it.numberAvailability?.toInt() == 0) {
                            Toast.makeText(
                                context,
                                "Mã giảm giá của quý khách không đúng hoặc đã hết hạn",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val proceed = (listSheet[i].price.toString().toDouble() - (listSheet[i].price.toString().toDouble()*it.percentDiscount!!.toDouble()/100)) * listSheet[i].numberOfPeople.toString().toDouble()
                            totalProcess += proceed
                            Log.i("test","----> ${formatStringPrice("$totalProcess")}")
                            binding.textProceed.text = formatStringPrice("$totalProcess")+" VND"
                        }
                    }
                } else {
                    val proceed = listSheet[i].price.toString().toDouble() * listSheet[i].numberOfPeople.toString().toDouble()
                    totalProcess += proceed
                    Log.i("test","----> ${formatStringPrice("$totalProcess")}")
                    binding.textProceed.text = formatStringPrice("$totalProcess")+" VND"
                }
            }
        }

        binding.refreshBtn.setOnClickListener {
            var totalProcess = 0.0
            firebaseService.getBookTourSheetWhere("status", arrayOfStatusSheet[1]) { listSheet ->
                for(i in listSheet.indices){
                    if (listSheet[i].discountCode != "") {
                        firebaseService.getDiscount(listSheet[i].discountCode.toString()) {
                            if (it.id == "" || it.numberAvailability?.toInt() == 0) {
                                Toast.makeText(
                                    context,
                                    "Mã giảm giá của quý khách không đúng hoặc đã hết hạn",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val proceed = (listSheet[i].price.toString().toDouble() - (listSheet[i].price.toString().toDouble()*it.percentDiscount!!.toDouble()/100)) * listSheet[i].numberOfPeople.toString().toDouble()
                                totalProcess += proceed
                                Log.i("test","----> ${formatStringPrice("$totalProcess")}")
                                binding.textProceed.text = formatStringPrice("$totalProcess")+" VND"
                            }
                        }
                    } else {
                        val proceed = listSheet[i].price.toString().toDouble() * listSheet[i].numberOfPeople.toString().toDouble()
                        totalProcess += proceed
                        Log.i("test","----> ${formatStringPrice("$totalProcess")}")
                        binding.textProceed.text = formatStringPrice("$totalProcess")+" VND"
                    }
                }
            }
        }

        return binding.root
    }
    private fun formatStringPrice(price: String): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toDouble()
        return formatter.format(myNumber)
    }
}