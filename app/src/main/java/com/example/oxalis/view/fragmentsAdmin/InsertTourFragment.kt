package com.example.oxalis.view.fragmentsAdmin

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.oxalis.databinding.FragmentInsertTourBinding
import com.example.oxalis.databinding.FragmentSearchBinding
import com.example.oxalis.model.StopPointInfo
import com.example.oxalis.service.FirebaseService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class InsertTourFragment : Fragment() {

    private var _binding: FragmentInsertTourBinding? = null
    private val binding get() = _binding!!

    private var index = 0
    private lateinit var imageUri: Uri
    private var arrayImageUri = ArrayList<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        _binding = FragmentInsertTourBinding.inflate(inflater, container, false)


        return binding.root
    }
}