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
import com.example.oxalis.adapter.BookTourAdapter
import com.example.oxalis.adapter.UserAdapter
import com.example.oxalis.databinding.FragmentManagerAccountBinding
import com.example.oxalis.databinding.FragmentManagerUserBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailUserFragment

class ManagerUserFragment : Fragment() {

    private var _binding: FragmentManagerUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var managerUserRecyclerView: RecyclerView
    private lateinit var managerUserAdapter: UserAdapter
    private val firebaseService = FirebaseService()
    var onClickItemManagerUserFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManagerUserBinding.inflate(inflater, container, false)

        firebaseService.getAllUserInfo {
            setManagerBookTourListRecycler(it)
        }

        binding.addingBtn.setOnClickListener {
            val addUserFragment = AddUserFragment()
            onClickItemManagerUserFragment?.invoke(addUserFragment)
            addUserFragment.onClickRepeat={
                if(it){
                    onClickRepeat?.invoke(true)
                }
            }
        }

        return binding.root
    }

    private fun setManagerBookTourListRecycler(listUserInfo: List<UserInfo>) {
        managerUserRecyclerView = binding.listAccountRecycler
        managerUserRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerUserAdapter = UserAdapter(requireContext(), listUserInfo)
        managerUserRecyclerView.adapter = managerUserAdapter
        managerUserAdapter.onItemClickChild = { userInfo->
            val detailUserFragment = DetailUserFragment(userInfo)
            onClickItemManagerUserFragment?.invoke(detailUserFragment)
            detailUserFragment.onClickRepeat={
                if(it){
                    onClickRepeat?.invoke(true)
                }
            }
        }
    }

}