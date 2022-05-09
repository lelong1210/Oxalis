package com.example.oxalis.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentDetailUserBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayStatusTour
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.service.FirebaseService


class DetailUserFragment(val userInfo: UserInfo) : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    var onClickRepeat: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)

        binding.nameOfUser.text = userInfo.fullname
        binding.idOfUser.setText(userInfo.id)
        binding.mailOfUser.setText(userInfo.mail)
        binding.phoneOfUser.setText(userInfo.phone)
        binding.slbSex.setText(userInfo.gender)
        binding.slbPermission.setText(userInfo.permission)
        binding.slbDate.setText(userInfo.date)
        binding.addressOfUser.setText(userInfo.address)
        binding.slbStatus.setText(userInfo.status)


        // select box status
        val itemsStatus = arrayStatusUserInfo
        val adapterStatus =
            ArrayAdapter(requireContext(), R.layout.list_item_select_box, itemsStatus)
        binding.slbStatus.setAdapter(adapterStatus)

        // avatar
        firebaseService.getOnlyImageUser(userInfo.avatar.toString()){
            Glide.with(this).load(it).into(binding.userAvatar)
        }

        binding.btnUpdateUser.setOnClickListener {
            userInfo.status = binding.slbStatus.text.toString()

            firebaseService.updateUser(userInfo){
                if(it){
                    Toast.makeText(
                        context,
                        "Đã cập nhật User ${userInfo.fullname}",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(
                        context,
                        "Cập nhật User ${userInfo.fullname} thất bại",
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
}