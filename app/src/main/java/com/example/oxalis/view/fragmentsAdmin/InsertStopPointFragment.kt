package com.example.oxalis.view.fragmentsAdmin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentInsertStopPointBinding
import com.example.oxalis.databinding.FragmentInsertTourBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.service.FirebaseService
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class InsertStopPointFragment : Fragment() {

    private var _binding: FragmentInsertStopPointBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri: Uri
    private var arrayImageUri = ArrayList<Uri>()
    private var index = 0
    private var firebaseService = FirebaseService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInsertStopPointBinding.inflate(inflater, container, false)

        val myCalender1 = Calendar.getInstance()
        val myCalender2 = Calendar.getInstance()
        val timePicker1 = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalender1.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalender1.set(Calendar.MINUTE, minute)
            binding.timeStartOfStopPoint.setText(updateUiCalender(myCalender1))
        }
        val timePicker2 = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalender2.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalender2.set(Calendar.MINUTE, minute)
            binding.timeEndOfStopPoint.setText(updateUiCalender(myCalender2))
        }
        val datePicker1 = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender1.set(Calendar.YEAR, year)
            myCalender1.set(Calendar.MONTH, month)
            myCalender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(
                requireContext(),
                timePicker1,
                myCalender1.get(Calendar.HOUR_OF_DAY),
                myCalender1.get(Calendar.MINUTE),
                true
            ).show()
        }
        val datePicker2 = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender2.set(Calendar.YEAR, year)
            myCalender2.set(Calendar.MONTH, month)
            myCalender2.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(
                requireContext(),
                timePicker2,
                myCalender2.get(Calendar.HOUR_OF_DAY),
                myCalender2.get(Calendar.MINUTE),
                true
            ).show()
        }


        val items = listOf("NHÀ HÀNG", "QUÁN NƯỚC", "KHÁCH SẠN", "KHU VUI CHƠI")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_select_box, items)
        binding.slbService.setAdapter(adapter)



        binding.timeStartOfStopPoint.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker1,
                myCalender1.get(Calendar.YEAR),
                myCalender1.get(Calendar.MONTH),
                myCalender1.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
        binding.timeEndOfStopPoint.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker2,
                myCalender2.get(Calendar.YEAR),
                myCalender2.get(Calendar.MONTH),
                myCalender2.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
        binding.imageOfStopPoint0.setOnClickListener {
            index = 0
            selectImage()
        }
        binding.imageOfStopPoint1.setOnClickListener {
            index = 1
            selectImage()
        }
        binding.imageOfStopPoint2.setOnClickListener {
            index = 2
            selectImage()
        }
        binding.imageOfStopPoint3.setOnClickListener {
            index = 3
            selectImage()
        }
        binding.btnAddStopPoint.setOnClickListener {
            var idStopPoint = "sp"
            firebaseService.getLastIdOfStopPoint { idLast ->
                idStopPoint += idLast
                val stopPoint = StopPointInfo(
                    idStopPoint,
                    binding.inputNameOfStopPoint.text.toString().uppercase(),
                    binding.slbService.text.toString().uppercase(),
                    binding.addressOfStopPoint.text.toString().uppercase(),
                    binding.priceOfStopPoint.text.toString(),
                    "${idStopPoint}0",
                    binding.timeStartOfStopPoint.text.toString(),
                    binding.timeEndOfStopPoint.text.toString(),
                    arrayListOf(
                        "${idStopPoint}0", "${idStopPoint}1", "${idStopPoint}2", "${idStopPoint}3"
                    ),
                    binding.contractOfStopPoint.text.toString(),
                    binding.describeOfStopPoint.text.toString()
                )

                firebaseService.insertStopPoint(stopPoint) { status ->
                    if (status) {
                        firebaseService.uploadImage(idStopPoint, arrayImageUri) { status ->
                            if (status) {
                                firebaseService.updateLastIdOfStopPoint(idLast.toInt()) { status ->
                                    if (status) {
                                        Toast.makeText(
                                            context,
                                            "Đã thêm điểm dừng chân",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }


        return binding.root
    }

    private fun updateUiCalender(myCalendar: Calendar): String {
        val myFormat = "yyyy-MM-dd HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        return sdf.format(myCalendar.time)
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!!
            when (index) {
                0 -> {
                    binding.imageOfStopPoint0.setImageURI(data?.data)
                }
                1 -> {
                    binding.imageOfStopPoint1.setImageURI(data?.data)
                }
                2 -> {
                    binding.imageOfStopPoint2.setImageURI(data?.data)
                }
                3 -> {
                    binding.imageOfStopPoint3.setImageURI(data?.data)
                }
                else -> {
                    binding.imageOfStopPoint3.setImageURI(data?.data)
                }
            }
            if (arrayImageUri.size >= 4) {
                arrayImageUri[index] = imageUri
            } else {
                arrayImageUri.add(imageUri)
            }

        }
    }
}