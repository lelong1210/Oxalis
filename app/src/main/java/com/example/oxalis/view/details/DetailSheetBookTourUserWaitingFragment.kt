package com.example.oxalis.view.details

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailSheetBookTourUserWaitingBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailSheetBookTourUserWaitingFragment(private val sheetBookTour:SheetAddInformationCart,private val tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentDetailSheetBookTourUserWaitingBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    var onItemClick:((Boolean)->Unit)?=null
    var onItemMoreClick:((TourInfo)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailSheetBookTourUserWaitingBinding.inflate(inflater,container,false)

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.calendarOfBookTour.setText(updateUiCalender(myCalender))
        }

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

        val items = listOf("NAM", "NỮ")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_select_box, items)
        binding.slbSex.setAdapter(adapter)



        binding.btnCancel.setOnClickListener {
            sheetBookTour.status = arrayOfStatusSheet[2]
            firebaseService.insertSheetAddInformationCart(sheetBookTour){
                if(it){
                    Toast.makeText(
                        context,
                        "Đã hủy Tour",
                        Toast.LENGTH_SHORT
                    ).show()
                    onItemClick?.invoke(true)
                }
            }
        }
        binding.btnUpdate.setOnClickListener {

            sheetBookTour.nameOfUserBookTour = binding.nameOfUserBook.text.toString().uppercase()
            sheetBookTour.gender = binding.slbSex.text.toString()
            sheetBookTour.mail = binding.mailOfUserBookTour.text.toString()
            sheetBookTour.address = binding.addressOfUserBookTour.text.toString()
            sheetBookTour.phone = binding.phoneOfUserBookTour.text.toString()
            sheetBookTour.timeStart = binding.calendarOfBookTour.text.toString()
            sheetBookTour.numberOfPeople = binding.numberOfPeople.text.toString()
            sheetBookTour.discountCode = binding.discountCode.text.toString()


            firebaseService.insertSheetAddInformationCart(sheetBookTour){
                if(it){
                    Toast.makeText(
                        context,
                        "Đã cập nhận phiếu đăng ký tour",
                        Toast.LENGTH_SHORT
                    ).show()
                    onItemClick?.invoke(true)
                }
            }
        }
        binding.btnMore.setOnClickListener {
            onItemMoreClick?.invoke(tourInfo)
        }

        return binding.root
    }
    private fun formatStringPrice(price: String): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = price.toDouble()
        return formatter.format(myNumber)
    }
    private fun updateUiCalender(myCalendar: Calendar): String {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        return sdf.format(myCalendar.time)
    }
}