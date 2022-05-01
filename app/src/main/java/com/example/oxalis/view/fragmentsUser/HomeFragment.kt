package com.example.oxalis.view.fragmentsUser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.R
import com.example.oxalis.adapter.HomeRecyclerAdapter
import com.example.oxalis.databinding.FragmentHomeBinding
import com.example.oxalis.model.AllCategory
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.login.LoginActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeCategoryRecycler:RecyclerView
    private lateinit var homeRecyclerAdapter:HomeRecyclerAdapter
    var onItemClick:((TourInfo)->Unit)? = null
    private lateinit var tourInfo: TourInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val tourItemList1:MutableList<TourInfo> = ArrayList()
        tourItemList1.add(TourInfo("A","1111",R.drawable.demoimage,5,"QUẢNG BÌNH"))
        tourItemList1.add(TourInfo("A","1111",R.drawable.demoimage,5,"QUẢNG BÌNH"))
        tourItemList1.add(TourInfo("A","1111",R.drawable.demoimage,5,"QUẢNG BÌNH"))
        tourItemList1.add(TourInfo("A","1111",R.drawable.demoimage,5,"QUẢNG BÌNH"))
        tourItemList1.add(TourInfo("A","1111",R.drawable.demoimage,5,"QUẢNG BÌNH"))

        val tourItemList2:MutableList<TourInfo> = ArrayList()
        tourItemList2.add(TourInfo("B","2222",R.drawable.demoimage,5,"QUẢNG TRỊ"))
        tourItemList2.add(TourInfo("B","2222",R.drawable.demoimage,5,"QUẢNG TRỊ"))
        tourItemList2.add(TourInfo("B","2222",R.drawable.demoimage,5,"QUẢNG TRỊ"))
        tourItemList2.add(TourInfo("B","2222",R.drawable.demoimage,5,"QUẢNG TRỊ"))
        tourItemList2.add(TourInfo("B","2222",R.drawable.demoimage,5,"QUẢNG TRỊ"))

        val tourItemList3:MutableList<TourInfo> = ArrayList()
        tourItemList3.add(TourInfo("C","3333",R.drawable.demoimage,5,"QUẢNG NAM"))
        tourItemList3.add(TourInfo("C","3333",R.drawable.demoimage,5,"QUẢNG NAM"))
        tourItemList3.add(TourInfo("C","3333",R.drawable.demoimage,5,"QUẢNG NAM"))
        tourItemList3.add(TourInfo("C","3333",R.drawable.demoimage,5,"QUẢNG NAM"))
        tourItemList3.add(TourInfo("C","3333",R.drawable.demoimage,5,"QUẢNG NAM"))

        val tourItemList4:MutableList<TourInfo> = ArrayList()
        tourItemList4.add(TourInfo("D","4444",R.drawable.demoimage,5,"QUẢNG NGÃI"))
        tourItemList4.add(TourInfo("D","4444",R.drawable.demoimage,5,"QUẢNG NGÃI"))
        tourItemList4.add(TourInfo("D","4444",R.drawable.demoimage,5,"QUẢNG NGÃI"))
        tourItemList4.add(TourInfo("D","4444",R.drawable.demoimage,5,"QUẢNG NGÃI"))
        tourItemList4.add(TourInfo("D","4444",R.drawable.demoimage,5,"QUẢNG NGÃI"))

        val tourItemList5:MutableList<TourInfo> = ArrayList()
        tourItemList5.add(TourInfo("E","5555",R.drawable.demoimage,5,"HÀ NỘI"))
        tourItemList5.add(TourInfo("E","5555",R.drawable.demoimage,5,"HÀ NỘI"))
        tourItemList5.add(TourInfo("E","5555",R.drawable.demoimage,5,"HÀ NỘI"))
        tourItemList5.add(TourInfo("E","5555",R.drawable.demoimage,5,"HÀ NỘI"))
        tourItemList5.add(TourInfo("E","5555",R.drawable.demoimage,5,"HÀ NỘI"))


        val allCategory: MutableList<AllCategory> = ArrayList()
        allCategory.add(AllCategory("A",tourItemList1))
        allCategory.add(AllCategory("B",tourItemList2))
        allCategory.add(AllCategory("C",tourItemList3))
        allCategory.add(AllCategory("D",tourItemList4))
        allCategory.add(AllCategory("E",tourItemList5))

        setHomeCategoryRecycler(allCategory)


        return binding.root
    }
    private fun setHomeCategoryRecycler(allCategory: List<AllCategory>){
        homeCategoryRecycler = binding.homeRecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        homeCategoryRecycler.layoutManager = layoutManager
        homeRecyclerAdapter = HomeRecyclerAdapter(requireContext(),allCategory)
        homeCategoryRecycler.adapter = homeRecyclerAdapter

        homeRecyclerAdapter.onItemClick = {TourInfo->
//            if(TourInfo.name.equals("A")){
//                val intent = Intent(activity, LoginActivity::class.java)
//                activity?.startActivity(intent)
//            }else{
//                onItemClick?.invoke("B")
//            }
            onItemClick?.invoke(TourInfo)
        }

    }
}