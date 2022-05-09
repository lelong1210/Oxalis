package com.example.oxalis.view.fragmentsAdmin

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentAddUserBinding
import com.example.oxalis.databinding.FragmentManagerTourBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayGender
import com.example.oxalis.model.arrayPermission
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.service.FirebaseService
import java.text.SimpleDateFormat
import java.util.*

class AddUserFragment : Fragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private val firebaseService = FirebaseService()
    var onClickRepeat: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)

        // select box status
        val itemsStatus = arrayStatusUserInfo
        val adapterStatus =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsStatus)
        binding.slbStatus.setAdapter(adapterStatus)

        // select box sex
        val itemsSex = arrayGender
        val adapterSex =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsSex)
        binding.slbSex.setAdapter(adapterSex)

        // select box permission
        val itemsPermission = arrayPermission
        val adapterPermission =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsPermission)
        binding.slbPermission.setAdapter(adapterPermission)

        // calender

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.slbDate.setText(updateUiCalender(myCalender))
        }

        binding.slbDate.setOnDismissListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

        binding.userAvatar.setOnClickListener {
            selectImage()
        }
        binding.btnAddUser.setOnClickListener {
            val userInfo = UserInfo(
                "",
                binding.nameOfUser.text.toString(),
                binding.mailOfUser.text.toString(),
                binding.phoneOfUser.text.toString(),
                binding.slbSex.text.toString(),
                binding.slbPermission.text.toString(),
                "",
                binding.slbDate.text.toString(),
                binding.addressOfUser.text.toString(),
                binding.slbStatus.text.toString()
            )
            val password = binding.passwordOfUser.text.toString()
            firebaseService.createAccountAuth(userInfo, password) { status,userInfo ->
                if (status && imageUri != null) {
                    firebaseService.uploadImageUser(userInfo.avatar.toString(), imageUri!!){
                        if(it){
                            Toast.makeText(
                                context,
                                "Thêm User ${userInfo.fullname} thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            onClickRepeat?.invoke(true)
                        }else{
                            Toast.makeText(
                                context,
                                "Thêm User ${userInfo.fullname} thất bại mail có thể đã được dùng",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }else{
                    Toast.makeText(
                        context,
                        "Thêm User ${userInfo.fullname} thất bại mail có thể đã được dùng",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.btnBack.setOnClickListener {
            onClickRepeat?.invoke(true)
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
}