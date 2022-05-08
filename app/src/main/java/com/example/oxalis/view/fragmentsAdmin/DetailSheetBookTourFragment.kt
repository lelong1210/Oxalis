package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailSheetBookTourBinding
import com.example.oxalis.databinding.FragmentHomeAdminBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailSheetBookTourFragment(private val sheetBookTour: SheetAddInformationCart) : Fragment() {

    private var _binding: FragmentDetailSheetBookTourBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    var onClickBtnConfirmOfBtnCancel: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailSheetBookTourBinding.inflate(inflater,container,false)

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

        if(sheetBookTour.discountCode != ""){
            firebaseService.getDiscount(sheetBookTour.discountCode.toString()) {
                binding.totalPriceOfTour.setText(
                    formatStringPrice("${(sheetBookTour.price.toString().toDouble() - (sheetBookTour.price.toString().toDouble()*it.percentDiscount!!.toDouble()/100)) * sheetBookTour.numberOfPeople.toString().toDouble()}")
                )
            }

        }
        else{
            binding.totalPriceOfTour.setText(
                formatStringPrice("${(sheetBookTour.price.toString().toDouble()) * sheetBookTour.numberOfPeople.toString().toDouble()}")
            )
        }

        binding.btnConfirm.setOnClickListener {
            sheetBookTour.status = arrayOfStatusSheet[1]
            firebaseService.insertSheetAddInformationCart(sheetBookTour){
                if(it){
                    Toast.makeText(
                        context,
                        "Đã xác nhận Tour",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClickBtnConfirmOfBtnCancel?.invoke(true)
                }
            }
        }
        binding.btnCancel.setOnClickListener {
            sheetBookTour.status = arrayOfStatusSheet[2]
            firebaseService.insertSheetAddInformationCart(sheetBookTour){
                if(it){
                    Toast.makeText(
                        context,
                        "Đã hủy Tour",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClickBtnConfirmOfBtnCancel?.invoke(true)
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