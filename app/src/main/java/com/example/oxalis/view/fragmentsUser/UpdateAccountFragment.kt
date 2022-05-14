package com.example.oxalis.view.fragmentsUser

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
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
import com.example.oxalis.databinding.FragmentManagerAccountBinding
import com.example.oxalis.databinding.FragmentUpdateAccountBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayGender
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.service.FirebaseService
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class UpdateAccountFragment(val userInfo: UserInfo) : Fragment() {

    private var _binding: FragmentUpdateAccountBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    private  var imageUri: Uri?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateAccountBinding.inflate(inflater, container, false)

        binding.nameOfUser.setText(userInfo.fullname)
        binding.mailOfUser.setText(userInfo.mail)
        binding.phoneOfUser.setText(userInfo.phone)
        binding.slbSex.setText(userInfo.gender)
        binding.slbDate.setText(userInfo.date)
        binding.addressOfUser.setText(userInfo.address)

        // select box sex
        val itemsSex = arrayGender
        val adapterSex =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsSex)
        binding.slbSex.setAdapter(adapterSex)

        // calender
        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.slbDate.setText(updateUiCalender(myCalender))
        }
        firebaseService.getOnlyImageUser(userInfo.avatar.toString()){
            Glide.with(this).load(it).into(binding.userAvatar)
        }

        binding.slbDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
        binding.userAvatar.setOnClickListener{
            selectImage()
        }
        binding.btnUpdateUser.setOnClickListener {
            userInfo.fullname = binding.nameOfUser.text.toString().uppercase()
            userInfo.phone = binding.phoneOfUser.text.toString().uppercase()
            userInfo.gender = binding.slbSex.text.toString().uppercase()
            userInfo.date = binding.slbDate.text.toString()
            userInfo.address = binding.addressOfUser.text.toString().uppercase()

            firebaseService.updateUser(userInfo){
                if(it){
                    if(imageUri != null)
                        firebaseService.uploadImageUser(userInfo.avatar.toString(),imageUri!!){
                            Toast.makeText(
                                context,
                                "Cập nhật User ${userInfo.fullname} thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            insertSharedPreferences(userInfo)
                        }else{
                        Toast.makeText(
                            context,
                            "Cập nhật User ${userInfo.fullname} thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        insertSharedPreferences(userInfo)
                    }

                }else{
                    Toast.makeText(
                        context,
                        "Cập nhật User ${userInfo.fullname} thất bại",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        return binding.root
    }
    private fun updateUiCalender(myCalendar: Calendar): String {
        val myFormat = "yyyy-MM-dd"
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
            binding.userAvatar.setImageURI(data.data)
        }
    }
    private fun insertSharedPreferences(userInfo: UserInfo) {
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        val gson = Gson()
        var json = gson.toJson(userInfo)
        editor?.putString("USERINFO", json)
        editor?.commit()
    }
}