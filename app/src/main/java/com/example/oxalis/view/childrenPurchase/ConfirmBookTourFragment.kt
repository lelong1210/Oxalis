package com.example.oxalis.view.childrenPurchase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.BookTourAdapter
import com.example.oxalis.databinding.FragmentConfirmBookTourBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailSheetBookTourUserConfirmFragment
import com.google.gson.Gson

class ConfirmBookTourFragment : Fragment() {

    private var _binding: FragmentConfirmBookTourBinding? = null
    private val binding get() = _binding!!
    var onItemClick: ((Boolean) -> Unit)? = null
    var onItemClickFragment: ((Fragment) -> Unit)? = null
    var onItemMoreClick: ((TourInfo) -> Unit)? = null
    var onItemRating: ((SheetAddInformationCart, TourInfo) -> Unit)? = null
    private val firebaseService = FirebaseService()
    private lateinit var managerBookTourRecyclerView: RecyclerView
    private lateinit var managerBookTourAdapter: BookTourAdapter
    private lateinit var userInfo: UserInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmBookTourBinding.inflate(inflater, container, false)
        userInfo = getUserInfo()
        firebaseService.getBookTourSheet("status", arrayOfStatusSheet[1], "${userInfo.id}") {
            setManagerBookTourListRecycler(it)
        }
        binding.refreshBtn.setOnClickListener {
            firebaseService.getBookTourSheet("status", arrayOfStatusSheet[1], "${userInfo.id}") {
                setManagerBookTourListRecycler(it)
            }
        }

        return binding.root
    }

    private fun setManagerBookTourListRecycler(listBookTour: List<SheetAddInformationCart>) {
        managerBookTourRecyclerView = binding.listConfirmBookTourRecyclerView
        managerBookTourRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerBookTourAdapter = BookTourAdapter(requireContext(), listBookTour)
        managerBookTourRecyclerView.adapter = managerBookTourAdapter
        managerBookTourAdapter.onClickItem = { sheetAddInformationCart ->
            firebaseService.getTourWhereId(sheetAddInformationCart.idTour.toString()) { tourInfo ->
                val detailSheetBookTourUserConfirmFragment =
                    DetailSheetBookTourUserConfirmFragment(sheetAddInformationCart, tourInfo)
                    onItemClickFragment?.invoke(detailSheetBookTourUserConfirmFragment)
                detailSheetBookTourUserConfirmFragment.onItemMoreClick = { tourInfo ->
                    onItemMoreClick?.invoke(tourInfo)
                }
                detailSheetBookTourUserConfirmFragment.onItemRating =
                    { sheetAddInformationCart, tourInfo ->
                        onItemRating?.invoke(sheetAddInformationCart, tourInfo)
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