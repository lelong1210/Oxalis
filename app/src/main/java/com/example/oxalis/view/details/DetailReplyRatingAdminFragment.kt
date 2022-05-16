package com.example.oxalis.view.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.RatingAdapter
import com.example.oxalis.adapter.ReplyAdapter
import com.example.oxalis.databinding.FragmentDetailReplyRatingAdminBinding
import com.example.oxalis.databinding.FragmentDetailSheetBookTourBinding
import com.example.oxalis.model.RatingTour
import com.example.oxalis.model.ReplyRatingTour
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DetailReplyRatingAdminFragment(private val ratingTour: RatingTour) : Fragment() {

    private var _binding: FragmentDetailReplyRatingAdminBinding? = null
    private val binding get() = _binding!!
    private val firebaseService = FirebaseService()
    private lateinit var listReplyCommentRecyclerView: RecyclerView
    private lateinit var ratingReplyAdapter: ReplyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailReplyRatingAdminBinding.inflate(inflater, container, false)

        firebaseService.getRelyRatingTourWhere(ratingTour.id.toString()) {


            setManagerReplyRatingListRecycler(it)
        }
        binding.sendReplyBtn.setOnClickListener {
            val content = binding.editTourRatingContent.text
            val currentTime = getCurrentTime()
            val userInfoAdmin = getUserInfo()
            val replyRatingTour = ReplyRatingTour(
                "Reply${ratingTour.id}${userInfoAdmin.id}",
                "${userInfoAdmin.id}",
                "${userInfoAdmin.fullname}",
                "${ratingTour.id}",
                "$content",
                "$currentTime"
            )

            firebaseService.insertRepLyRatingTour(replyRatingTour) {
                if (it) {
                    Toast.makeText(requireContext(), "Đã phản hồi", Toast.LENGTH_SHORT).show()

                    firebaseService.getRelyRatingTourWhere(ratingTour.id.toString()) { list ->
                        setManagerReplyRatingListRecycler(list)
                    }

                } else {
                    Toast.makeText(requireContext(), "Phản hồi thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun setManagerReplyRatingListRecycler(listReplyComment: List<ReplyRatingTour>) {
        listReplyCommentRecyclerView = binding.listReplyCommentRecycler
        listReplyCommentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ratingReplyAdapter = ReplyAdapter(requireContext(), listReplyComment)
        listReplyCommentRecyclerView.adapter = ratingReplyAdapter
    }

    private fun getCurrentTime(): String {
        val myFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return myFormat.format(Date())
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = activity?.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")
        return gson.fromJson(json, UserInfo::class.java)
    }
}