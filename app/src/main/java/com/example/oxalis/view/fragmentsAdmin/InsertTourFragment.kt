package com.example.oxalis.view.fragmentsAdmin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentInsertTourBinding
import com.example.oxalis.databinding.FragmentSearchBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class InsertTourFragment : Fragment() {

    private var _binding: FragmentInsertTourBinding? = null
    private val binding get() = _binding!!

    private var index = 0
    private lateinit var imageUri: Uri
    private var arrayImageUri = ArrayList<Uri>()
    private var firebaseService = FirebaseService()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInsertTourBinding.inflate(inflater, container, false)

        // select box status
        val itemsStatus = listOf("CÓ THỂ ĐẶT", "KHÔNG KHÔNG THỂ ĐẶT")
        val adapterStatus =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsStatus)
        binding.slbStatus.setAdapter(adapterStatus)
        // select box permission
        val itemsPermission = listOf("ẨN", "HIỆN")
        val adapterPermission =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsPermission)
        binding.slbPermission.setAdapter(adapterPermission)
        // select box type
        val itemsType = listOf("THAM QUAN", "VĂN HÓA","MẠO HIỂM","ẨM THỰC","TEAMBUILDING")
        val adapterType =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsType)
        binding.slbTypeOfTour.setAdapter(adapterType)
        // set on click
        binding.imageOfTourInfo0.setOnClickListener {
            index = 0
            selectImage()
        }
        binding.imageOfTourInfo1.setOnClickListener {
            index = 1
            selectImage()
        }
        binding.imageOfTourInfo2.setOnClickListener {
            index = 2
            selectImage()
        }
        binding.imageOfTourInfo3.setOnClickListener {
            index = 3
            selectImage()
        }
        binding.btnAddTour.setOnClickListener {
            var idOfTourInfo = "tour"
            firebaseService.getLastIdOfTourInfo { idLast ->
                idOfTourInfo += idLast
                val tourInfo = TourInfo(
                    idOfTourInfo,
                    binding.slbStatus.text.toString().uppercase(),
                    binding.nameOfTour.text.toString().uppercase(),
                    binding.addressOfTour.text.toString().uppercase(),
                    binding.priceOfTour.text.toString().uppercase(),
                    binding.timeStartOfTour.text.toString().uppercase(),
                    binding.timeLongOfTour.text.toString().uppercase(),
                    binding.ageOfTour.text.toString().uppercase(),
                    binding.slbPermission.text.toString().uppercase(),
                    "${idOfTourInfo}0",
                    null,
                    binding.describeOfTour.text.toString(),
                    binding.slbTypeOfTour.text.toString(),
                    binding.percent.text.toString(),
                    arrayListOf("${idOfTourInfo}1", "${idOfTourInfo}2", "${idOfTourInfo}3")
                )

                Toast.makeText(
                    context,
                    "Tour đang được thêm vui lòng đợi",
                    Toast.LENGTH_SHORT
                ).show()

                firebaseService.insertTourInfo(tourInfo) { status ->
                    if (status) {
                        firebaseService.uploadImage(idOfTourInfo, arrayImageUri) { status ->
                            if (status) {
                                firebaseService.updateLastIdOfTourInfo(idLast.toInt()) { status ->
                                    if (status) {
                                        Toast.makeText(
                                            context,
                                            "Đã thêm tour",
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
                    binding.imageOfTourInfo0.setImageURI(data?.data)
                }
                1 -> {
                    binding.imageOfTourInfo1.setImageURI(data?.data)
                }
                2 -> {
                    binding.imageOfTourInfo2.setImageURI(data?.data)
                }
                3 -> {
                    binding.imageOfTourInfo3.setImageURI(data?.data)
                }
                else -> {
                    binding.imageOfTourInfo3.setImageURI(data?.data)
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