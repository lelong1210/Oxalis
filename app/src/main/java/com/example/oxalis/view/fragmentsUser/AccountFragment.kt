package com.example.oxalis.view.fragmentsUser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.databinding.FragmentAccountBinding
import com.example.oxalis.view.login.LoginActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater,container,false)

        binding.testBTN.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
        }
        return binding.root
    }

}