package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.TourInfoItemAdapter
import com.example.oxalis.databinding.FragmentManagerTourBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailTourInfoAdminFragment

class ManagerTourFragment : Fragment() {

    private var _binding: FragmentManagerTourBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    private lateinit var managerTourInfoRecyclerView:RecyclerView
    private lateinit var managerTourInfoAdapter:TourInfoItemAdapter
    var onItemClickManagerTourInfoFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat:((Boolean)->Unit)?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerTourBinding.inflate(inflater, container, false)

        firebaseService.getAllTourInfo {
            setManagerTourInfoListRecycler(it)
        }

        binding.addingBtn.setOnClickListener {
            val insertTourFragment = InsertTourFragment()
            onItemClickManagerTourInfoFragment?.invoke(insertTourFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setManagerTourInfoListRecycler(listTourInfo: List<TourInfo>) {
        managerTourInfoRecyclerView = binding.listTourRecycler
        managerTourInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerTourInfoAdapter = TourInfoItemAdapter(requireContext(),1, listTourInfo)
        managerTourInfoRecyclerView.adapter = managerTourInfoAdapter
        managerTourInfoAdapter.onItemClickChild={
            var detailTourInfoAdmin = DetailTourInfoAdminFragment(it)
            onItemClickManagerTourInfoFragment?.invoke(detailTourInfoAdmin)
            detailTourInfoAdmin.onClickRepeat={
                onClickRepeat?.invoke(true)
            }
        }
    }
}