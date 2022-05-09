package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.UserAdapter
import com.example.oxalis.databinding.FragmentManagerAccountBinding
import com.example.oxalis.service.FirebaseService

class ManagerAccountFragment : Fragment() {

    private var _binding: FragmentManagerAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var managerAccountRecyclerView: RecyclerView
    private lateinit var managerUserAdapter: UserAdapter
    private val firebaseService = FirebaseService()
    var onClickItemManagerAccountFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManagerAccountBinding.inflate(inflater, container, false)


        return binding.root
    }

}