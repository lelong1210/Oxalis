package com.example.oxalis.view.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailAccountBinding
import com.example.oxalis.databinding.FragmentDetailDiscountBinding
import com.example.oxalis.databinding.FragmentDetailUserBinding
import com.example.oxalis.service.FirebaseService

class DetailAccountFragment(val mail: String) : Fragment() {

    private var _binding: FragmentDetailAccountBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    var changePassSuccess: ((Boolean) -> Unit)? = null
    var onClickRepeat: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailAccountBinding.inflate(inflater, container, false)



        binding.btnUpdate.setOnClickListener {

            val pass = binding.password.text.toString()
            val newPass = binding.newPassword.text.toString()
            val rePass = binding.reNewPassword.text.toString()

            if(pass != "" && newPass != "" && rePass != "" && newPass == rePass){
                firebaseService.login(
                    mail,
                    binding.password.text.toString()
                ) { userInfo, statusParent ->
                    if (statusParent) {
                        firebaseService.updatePassword(binding.newPassword.text.toString()) { status ->
                            if (status) {
                                changePassSuccess?.invoke(true)
                                Toast.makeText(
                                    context,
                                    "Đã cập nhật mật khẩu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                changePassSuccess?.invoke(false)
                                Toast.makeText(
                                    context,
                                    "Cập nhật mật khẩu thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Mật khẩu cũ không đúng",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                Toast.makeText(
                    context,
                    "Các ô không được để trống hoặc mật khẩu mới và nhập lại mật khẩu mới không giống nhau",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.btnBack.setOnClickListener {
            onClickRepeat?.invoke(true)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        onClickRepeat?.invoke(true)
    }
}