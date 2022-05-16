package com.example.oxalis.view.fragmentsUser

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.BookTourAdapter
import com.example.oxalis.adapter.TourInfoItemAdapter
import com.example.oxalis.databinding.FragmentConfirmBookTourBinding
import com.example.oxalis.databinding.FragmentWaitingBookTourBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailSheetBookTourFragment
import com.example.oxalis.view.details.DetailTourInfoAdminFragment
import com.google.gson.Gson

class WaitingBookTourFragment() : Fragment() {


    private var _binding: FragmentWaitingBookTourBinding? = null
    private val binding get() = _binding!!
    var onItemClickFragment: ((Fragment) -> Unit)? = null
    var onItemMoreClick:((TourInfo)->Unit)?=null
    private val firebaseService = FirebaseService()
    private lateinit var managerBookTourRecyclerView: RecyclerView
    private lateinit var managerBookTourAdapter: BookTourAdapter
    private lateinit var userInfo: UserInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWaitingBookTourBinding.inflate(inflater, container, false)
        userInfo = getUserInfo()
        firebaseService.getBookTourSheet("status", arrayOfStatusSheet[0], "${userInfo.id}") {
            setManagerBookTourListRecycler(it)
        }
        binding.refreshBtn.setOnClickListener {
            firebaseService.getBookTourSheet("status", arrayOfStatusSheet[0], "${userInfo.id}") {
                setManagerBookTourListRecycler(it)
            }
        }

        return binding.root
    }

    private fun setManagerBookTourListRecycler(listBookTour: List<SheetAddInformationCart>) {
        managerBookTourRecyclerView = binding.listWaitingBookTourRecyclerView
        managerBookTourRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerBookTourAdapter = BookTourAdapter(requireContext(), listBookTour)
        managerBookTourRecyclerView.adapter = managerBookTourAdapter
        managerBookTourAdapter.onClickItem = { sheetAddInformationCart ->
            firebaseService.getTourWhereId(sheetAddInformationCart.idTour.toString()) { tourInfo ->
                val detailSheetBookTourUserWaitingFragment =
                    DetailSheetBookTourUserWaitingFragment(sheetAddInformationCart, tourInfo)
                onItemClickFragment?.invoke(detailSheetBookTourUserWaitingFragment)
                detailSheetBookTourUserWaitingFragment.onItemClick = {
                    firebaseService.getBookTourSheet(
                        "status",
                        arrayOfStatusSheet[0],
                        "${userInfo.id}"
                    ) {
                        setManagerBookTourListRecycler(it)
                    }
                }
                detailSheetBookTourUserWaitingFragment.onItemMoreClick = { tourInfo ->
                    onItemMoreClick?.invoke(tourInfo)
                }
            }
        }
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }

}