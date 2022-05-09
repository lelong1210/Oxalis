package com.example.oxalis.view.details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailTourInfoAdminBinding
import com.example.oxalis.model.*
import com.example.oxalis.service.FirebaseService

@Suppress("DEPRECATION")
class DetailTourInfoAdminFragment(private val tourInfo: TourInfo) : Fragment() {
    private var _binding: FragmentDetailTourInfoAdminBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    private lateinit var imageUri: Uri
    private var index = -1
    private var arrayImageUri = ArrayList<Uri>()
    var onClickRepeat: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTourInfoAdminBinding.inflate(inflater, container, false)

        binding.addressOfTour.setText(tourInfo.adrress)
        binding.timeStartOfTour.setText(tourInfo.timeStart)
        binding.nameOfTour.setText(tourInfo.name)
        binding.priceOfTour.setText(tourInfo.price)
        binding.slbStatus.setText(tourInfo.status)
        binding.ageOfTour.setText(tourInfo.age)
        binding.describeOfTour.setText(tourInfo.describe)
        binding.timeLongOfTour.setText(tourInfo.timeOfTour)
        binding.slbPermission.setText(tourInfo.permission)
        binding.slbTypeOfTour.setText(tourInfo.type)
        binding.percent.setText(tourInfo.discount)
        binding.rateOfTour.setText(tourInfo.rate)


        firebaseService.getUrlImage(tourInfo.id.toString(), arrayOfImage) {
            Glide.with(this).load(it[0]).into(binding.imageOfTourInfo0)
            Glide.with(this).load(it[1]).into(binding.imageOfTourInfo1)
            Glide.with(this).load(it[2]).into(binding.imageOfTourInfo2)
            Glide.with(this).load(it[3]).into(binding.imageOfTourInfo3)
        }

        // select box status
        val itemsStatus = arrayStatusTour
        val adapterStatus =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsStatus)
        binding.slbStatus.setAdapter(adapterStatus)
        // select box permission
        val itemsPermission =  arrayDisplay
        val adapterPermission =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsPermission)
        binding.slbPermission.setAdapter(adapterPermission)
        // select box type
        val itemsType = arrayItemTypeTour
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

        binding.btnUpdateTour.setOnClickListener {
            val idOfTourInfo = tourInfo.id
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
                "Tour đang được cập nhật vui lòng đợi",
                Toast.LENGTH_SHORT
            ).show()

            firebaseService.insertTourInfo(tourInfo) {
                if (it) {
                    if (index != -1) {
                        firebaseService.uploadImage(
                            tourInfo.id.toString(),
                            arrayImageUri
                        ) { status ->
                            if (status) {
                                Toast.makeText(
                                    context,
                                    "Cập nhật tour thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Cập nhật tour thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener {
            onClickRepeat?.invoke(true)
        }

        // Inflate the layout for this fragment
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