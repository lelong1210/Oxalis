package com.example.oxalis.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.databinding.FragmentDetailSheetBookTourUserConfirmBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat


class DetailSheetBookTourUserConfirmFragment(private val sheetBookTour: SheetAddInformationCart, private val tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentDetailSheetBookTourUserConfirmBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    var onItemMoreClick:((TourInfo)->Unit)?=null
    var onItemRating:((SheetAddInformationCart,TourInfo)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailSheetBookTourUserConfirmBinding.inflate(inflater,container,false)

        binding.addressOfUserBookTour.setText(sheetBookTour.address)
        binding.calendarOfBookTour.setText(sheetBookTour.timeStart)
        binding.nameOfUserBook.setText(sheetBookTour.nameOfUserBookTour)
        binding.inputNameOfTour.setText(sheetBookTour.nameOfTour)
        binding.slbSex.setText(sheetBookTour.gender)
        binding.mailOfUserBookTour.setText(sheetBookTour.mail)
        binding.phoneOfUserBookTour.setText(sheetBookTour.phone)
        binding.numberOfPeople.setText(sheetBookTour.numberOfPeople)
        binding.discountCode.setText(sheetBookTour.discountCode)
        binding.priceOfTour.setText(formatStringPrice(sheetBookTour.price.toString()))

        if (binding.discountCode.text.toString() != "") {
            firebaseService.getDiscount(binding.discountCode.text.toString()) {
                if (it.id == "" || it.numberAvailability?.toInt() == 0) {
                    Toast.makeText(
                        context,
                        "Mã giảm giá của quý khách không đúng hoặc đã hết hạn",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.totalPriceOfTour.setText(
                        formatStringPrice(
                            "${
                                (tourInfo.price.toString().toDouble() - (tourInfo.price.toString().toDouble()*it.percentDiscount!!.toDouble()/100)) * binding.numberOfPeople.text.toString().toDouble()
                            }"
                        )
                    )
                }
            }
        } else {
            binding.totalPriceOfTour.setText(
                formatStringPrice(
                    "${
                        tourInfo.price.toString()
                            .toDouble() * binding.numberOfPeople.text.toString().toDouble()
                    }"
                )
            )
        }
        binding.btnMore.setOnClickListener {
            onItemMoreClick?.invoke(tourInfo)
        }
        binding.btnRating.setOnClickListener {
            onItemRating?.invoke(sheetBookTour,tourInfo)
        }


        return binding.root
    }
    private fun formatStringPrice(price: String): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toDouble()
        return formatter.format(myNumber)
    }

}