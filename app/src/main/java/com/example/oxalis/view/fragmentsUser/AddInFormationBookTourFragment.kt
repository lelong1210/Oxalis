package com.example.oxalis.view.fragmentsUser

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentAddInFormationBookTourBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AddInFormationBookTourFragment(private val userInfo: UserInfo, val tourInfo: TourInfo) :
    Fragment() {


    private var _binding: FragmentAddInFormationBookTourBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddInFormationBookTourBinding.inflate(inflater, container, false)

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.calendarOfBookTour.setText(updateUiCalender(myCalender))
        }

        binding.slbSex.setText(userInfo.gender)

        val items = listOf("NAM", "NỮ")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_select_box, items)
        binding.slbSex.setAdapter(adapter)

        binding.addressOfUserBookTour.setText(userInfo.address)
        binding.mailOfUserBookTour.setText(userInfo.mail)
        binding.phoneOfUserBookTour.setText(userInfo.phone)
        binding.nameOfUserBook.setText(userInfo.fullname)
        binding.inputNameOfTour.setText(tourInfo.name)
        binding.priceOfTour.setText(formatStringPrice(tourInfo.price.toString()))

        binding.totalPriceOfTour.setOnClickListener {
            if (binding.numberOfPeople.text.toString() == "") {
                Toast.makeText(context, "Quý Khách Chưa Nhập Số Người", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.discountCode.text.toString() != "") {
                    firebaseService.getDiscount(binding.discountCode.text.toString()) {
                        if (it.id == "" || it.numberAvailability?.toInt() == 0) {
                            Toast.makeText(
                                context,
                                "Mã giảm giá của quý khách không đúng hoặc đã hết hạn",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            Toast.makeText(
                                context,
                                "Mã giảm giá của quý khách áp dụng thành công",
                                Toast.LENGTH_SHORT
                            ).show()

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
            }
        }

        binding.calendarOfBookTour.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

        binding.bookTourSheet.setOnClickListener {
            firebaseService.getLastId("SheetAddInformationCart", "lastId") { lastId ->
                val sheetAddInformationCart = SheetAddInformationCart(
                    "sheetBookTour$lastId",
                    userInfo.id,
                    binding.nameOfUserBook.text.toString().uppercase(),
                    tourInfo.id,
                    tourInfo.name,
                    binding.slbSex.text.toString().uppercase(),
                    binding.mailOfUserBookTour.text.toString(),
                    binding.addressOfUserBookTour.text.toString().uppercase(),
                    binding.phoneOfUserBookTour.text.toString().uppercase(),
                    binding.calendarOfBookTour.text.toString().uppercase(),
                    binding.numberOfPeople.text.toString().uppercase(),
                    arrayOfStatusSheet[0],
                    binding.discountCode.text.toString(),
                    tourInfo.price
                )
                firebaseService.insertSheetAddInformationCart(sheetAddInformationCart) { status ->
                    if (status) {
                        firebaseService.updateLastId(
                            "SheetAddInformationCart",
                            "lastId",
                            lastId.toInt()
                        ) {
                            if (status) {
                                Toast.makeText(
                                    context,
                                    "Quý Đã đặt tour đang chờ admin xác nhận",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Quý khách đã đặt tour thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Quý khách đã đặt tour thất bại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

//
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateUiCalender(myCalendar: Calendar): String {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        return sdf.format(myCalendar.time)
    }

    private fun formatStringPrice(price: String): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toDouble()
        return formatter.format(myNumber)
    }

}
