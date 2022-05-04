package com.example.oxalis.view.fragmentsUser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.MainActivity
import com.example.oxalis.databinding.FragmentAccountBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService
import com.google.gson.Gson

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfo: UserInfo
    private val firebaseService = FirebaseService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        userInfo = getUserInfo()

        binding.userInfoName.text = userInfo.fullname

        binding.btnLogoutAccount.setOnClickListener {
//            firebaseService.updatePassword("lelong1210") { status ->
//                if (status) {
//                    Toast.makeText(context, "Update password succeed", Toast.LENGTH_SHORT).show()
//                    logOut()
//                }
//            }
            if(logOut()){
                Toast.makeText(context,"logout succeed",Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return binding.root
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }

    private fun logOut(): Boolean {
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.remove("USERINFO")
        return editor?.commit()!!
    }


}