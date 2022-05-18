package com.example.oxalis.view.fragmentsAdmin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentHomeAdminBinding
import com.example.oxalis.databinding.FragmentInsertStopPointBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailTourInfoActivity
import com.google.gson.Gson
import java.util.ArrayList


class HomeAdminFragment : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    var onCardViewClick: ((Fragment) -> Unit)? = null
    var backLogin:((Boolean)->Unit)?=null
    var onItemMoreClick: ((TourInfo) -> Unit)? = null
    var backLogOut:((Boolean)->Unit)?=null
    var moveActivityChatAdmin:((userInfo:String,messenger:String)->Unit)?=null
    var onClickRepeatAndRm: ((Boolean) -> Unit)? = null
    private val managerBookTourFragment = ManagerBookTourFragment()
    private val managerDiscountFragment = ManagerDiscountFragment()
    private val managerTourFragment = ManagerTourFragment()
    private val managerUserFragment = ManagerUserFragment()
    private val managerAccountFragment = ManagerAccountFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)

        binding.cardViewStatistical.setOnClickListener {
//            backLogOut?.invoke(true)
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
            onCardViewClick?.invoke(managerAccountFragment)
        }
        binding.btnMess.setOnClickListener {
            val chatAdminFragment = ChatAdminFragment()
            onCardViewClick?.invoke(chatAdminFragment)
            // chat
            chatAdminFragment.onClickItemChatAdminFragment={
                onCardViewClick?.invoke(it)
            }
            chatAdminFragment.moveActivityChatAdmin={user,mess->
                moveActivityChatAdmin?.invoke(user,mess)
            }
            chatAdminFragment.onClickRepeat={
                Log.i("test","--> $it")
//            onCardViewClick?.invoke(chatAdminFragment)
            }
            chatAdminFragment.onClickRepeatAndRm={
                onClickRepeatAndRm?.invoke(it)
            }

        }


        // account
        managerAccountFragment.onClickItemManagerAccountFragment={
            onCardViewClick?.invoke(it)
        }
        managerAccountFragment.backLogin={
            if (it){
                backLogin?.invoke(true)
            }
        }
        managerAccountFragment.onClickRepeat={
            if (it){
                onCardViewClick?.invoke(managerAccountFragment)
            }
        }
        managerAccountFragment.backLogOut ={
            backLogOut?.invoke(true)
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
        managerBookTourFragment.onItemMoreClick={
            tourInfo->onItemMoreClick?.invoke(tourInfo)

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

}
