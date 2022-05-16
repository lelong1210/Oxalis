package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.RatingAdapter
import com.example.oxalis.databinding.FragmentChatAdminBinding
import com.example.oxalis.databinding.FragmentListRatingOfTourBinding
import com.example.oxalis.model.RatingTour
import com.example.oxalis.model.TourInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailReplyRatingAdminFragment

class ListRatingOfTourFragment(val tourInfo: TourInfo) : Fragment() {

    private var _binding: FragmentListRatingOfTourBinding? = null
    private val binding get() = _binding!!

    private lateinit var listCommentRecyclerView: RecyclerView
    private lateinit var ratingAdapter: RatingAdapter
    private val firebaseService = FirebaseService()
    var onItemFragment:((Fragment)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListRatingOfTourBinding.inflate(inflater, container, false)

        firebaseService.getRatingTourWhere(tourInfo.id.toString()){
            setManagerRatingListRecycler(it)
        }

        return binding.root
    }

    private fun setManagerRatingListRecycler(listComment: List<RatingTour>) {
        listCommentRecyclerView = binding.listCommentRecycler
        listCommentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ratingAdapter = RatingAdapter(requireContext(), listComment,false)
        listCommentRecyclerView.adapter = ratingAdapter
        ratingAdapter.onItemClick={
            val detailReplyRatingAdminFragment = DetailReplyRatingAdminFragment(it)
            onItemFragment?.invoke(detailReplyRatingAdminFragment)
        }

    }
}