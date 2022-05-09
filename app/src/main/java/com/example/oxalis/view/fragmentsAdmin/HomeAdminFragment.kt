package com.example.oxalis.view.fragmentsAdmin

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentHomeAdminBinding
import com.example.oxalis.databinding.FragmentInsertStopPointBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import java.util.ArrayList


class HomeAdminFragment : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    var onCardViewClick: ((Fragment) -> Unit)? = null
    private val insertTourFragment = InsertTourFragment()
    private val managerBookTourFragment = ManagerBookTourFragment()
    private val managerDiscountFragment = ManagerDiscountFragment()
    private val managerTourFragment = ManagerTourFragment()
    private val managerUserFragment = ManagerUserFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)

        binding.cardViewStatistical.setOnClickListener {
            Log.i("test", "cardViewStatistical")
        }
        binding.cardViewManagerTour.setOnClickListener {
            onCardViewClick?.invoke(managerTourFragment)
        }
        binding.cardViewManagerBookTour.setOnClickListener {
            onCardViewClick?.invoke(managerBookTourFragment)
        }
        binding.cardViewManagerUser.setOnClickListener {
            onCardViewClick?.invoke(managerUserFragment)
        }
        binding.cardViewManagerDiscount.setOnClickListener {
            onCardViewClick?.invoke(managerDiscountFragment)
        }
        binding.cardViewManagerAccount.setOnClickListener {
            if (logOut()) {
                Log.i("test", "cardViewManagerAccount")
            }
        }
        // user
        managerUserFragment.onClickItemManagerUserFragment={
            onCardViewClick?.invoke(it)
        }
        managerUserFragment.onClickRepeat={
            if(it){
                onCardViewClick?.invoke(managerUserFragment)
            }
        }
        // book tour
        managerBookTourFragment.onClickItemManagerBookTourFragment={
            onCardViewClick?.invoke(it)
        }
        managerBookTourFragment.onClickRepeat={
            if(it){
                onCardViewClick?.invoke(managerBookTourFragment)
            }
        }
        // tour
        managerTourFragment.onItemClickManagerTourInfoFragment={
            onCardViewClick?.invoke(it)
        }
        managerTourFragment.onClickRepeat={
            onCardViewClick?.invoke(managerTourFragment)
        }
        // discount
        managerDiscountFragment.onItemClickManagerDiscountFragment={
            onCardViewClick?.invoke(it)
        }
        managerDiscountFragment.onClickRepeat={
            if(it){
                onCardViewClick?.invoke(managerDiscountFragment)
            }
        }



        return binding.root
    }

    private fun logOut(): Boolean {
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.remove("USERINFO")
        return editor?.commit()!!
    }

}
