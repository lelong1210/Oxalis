package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.BookTourAdapter
import com.example.oxalis.databinding.FragmentManagerBookTourBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailSheetBookTourFragment

class ManagerBookTourFragment : Fragment() {

    private var _binding: FragmentManagerBookTourBinding? = null
    private val binding get() = _binding!!

    private lateinit var managerBookTourRecyclerView: RecyclerView
    private lateinit var managerBookTourAdapter: BookTourAdapter
    private val firebaseService = FirebaseService()
    var onClickItemManagerBookTourFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManagerBookTourBinding.inflate(inflater, container, false)

        firebaseService.getAllSheetBookTour {
            setManagerBookTourListRecycler(it)
        }

        return binding.root
    }

    private fun setManagerBookTourListRecycler(listBookTour: List<SheetAddInformationCart>) {
        managerBookTourRecyclerView = binding.listBookTourRecycler
        managerBookTourRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerBookTourAdapter = BookTourAdapter(requireContext(), listBookTour)
        managerBookTourRecyclerView.adapter = managerBookTourAdapter
        managerBookTourAdapter.onClickItem = { sheetAddInformationCart ->
            val detailSheetBookTourFragment = DetailSheetBookTourFragment(sheetAddInformationCart)
            onClickItemManagerBookTourFragment?.invoke(detailSheetBookTourFragment)

            detailSheetBookTourFragment.onClickRepeat={
                onClickRepeat?.invoke(it)
            }
        }
    }

}