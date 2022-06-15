package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.btnSearch.setOnClickListener {
            val value = binding.searchTour.text
            if(value.toString() != ""){
                firebaseService.getAllTourInfoWhere(value.toString()) {
                    setManagerTourInfoListRecycler(it)
                    Toast.makeText(context,"Có ${it.size} kết quả được tìm thấy",Toast.LENGTH_SHORT).show()
                }
            }
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
            detailTourInfoAdmin.onClickListRating={tourInfo->
                var listRatingOfTourFragment = ListRatingOfTourFragment(tourInfo)
                onItemClickManagerTourInfoFragment?.invoke(listRatingOfTourFragment)
                listRatingOfTourFragment.onItemFragment={fragment->
                    onItemClickManagerTourInfoFragment?.invoke(fragment)
                }
            }
        }
    }
}
/*
*
*
*
*
*
*
package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var index = 0
    private var isPrev = false
    private var isNext = true
    private var firebaseService = FirebaseService()
    private lateinit var managerTourInfoRecyclerView: RecyclerView
    private lateinit var managerTourInfoAdapter: TourInfoItemAdapter
    private var arrayList = ArrayList<TourInfo>()
    var onItemClickManagerTourInfoFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat: ((Boolean) -> Unit)? = null
    var arraySub = ArrayList<List<TourInfo>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerTourBinding.inflate(inflater, container, false)

        firebaseService.getAllTourInfo { list ->
            arrayList = list as ArrayList<TourInfo>
            arraySub = arrayListOf(
                arrayList.subList(0, 5),
                arrayList.subList(5, 10),
                arrayList.subList(10, 15),
                arrayList.subList(15, 20),
                arrayList.subList(20, 25)
            )
            Log.i("test","${list[5]}")
            setManagerTourInfoListRecycler(arraySub[0])
            index++
        }

        binding.btnNext.setOnClickListener {
            Log.i("test","$index")
            if(index != arraySub.size){
                var subList =  arraySub[index]
                Log.i("test","$subList")
                managerTourInfoAdapter.tourInfoList = subList
                managerTourInfoAdapter.notifyDataSetChanged()
                index++
            }else {
                Toast.makeText(context, "Hết Tour Để Hiển Thị", Toast.LENGTH_LONG).show()
            }

//            var indexLast = index + 5
//            Log.i("test", "NEXT _>  index $index - indexLast $indexLast - ${arrayList.size}")
//            if (isNext) {
//                var subList = if (indexLast > arrayList.size) {
//                    arrayList.subList(index, arrayList.size)
//                } else {
//                    arrayList.subList(index, indexLast)
//                }
//                managerTourInfoAdapter.tourInfoList = subList
//                managerTourInfoAdapter.notifyDataSetChanged()
//                isPrev = true
//            } else {
//                Toast.makeText(context, "Hết Tour Để Hiển Thị", Toast.LENGTH_LONG).show()
//            }
//            index = indexLast
//            if (indexLast > arrayList.size) {
//                isNext = false
//                index = arrayList.size
//            }
        }

        binding.btnPrev.setOnClickListener {
            if(index > 0){
                index -= 1
                Log.i("test","$index")
                var subList =  arraySub[index]
                managerTourInfoAdapter.tourInfoList = subList
                managerTourInfoAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Bạn đang ở trang đầu", Toast.LENGTH_LONG).show()
            }
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
        managerTourInfoAdapter = TourInfoItemAdapter(requireContext(), 1, listTourInfo)
        managerTourInfoRecyclerView.adapter = managerTourInfoAdapter
        managerTourInfoAdapter.onItemClickChild = {
            var detailTourInfoAdmin = DetailTourInfoAdminFragment(it)
            onItemClickManagerTourInfoFragment?.invoke(detailTourInfoAdmin)
            detailTourInfoAdmin.onClickRepeat = {
                onClickRepeat?.invoke(true)
                index = 0
                isNext = true
            }
            detailTourInfoAdmin.onClickListRating = { tourInfo ->
                var listRatingOfTourFragment = ListRatingOfTourFragment(tourInfo)
                onItemClickManagerTourInfoFragment?.invoke(listRatingOfTourFragment)
                listRatingOfTourFragment.onItemFragment = { fragment ->
                    onItemClickManagerTourInfoFragment?.invoke(fragment)
                }
            }
        }
    }
}
/*//            var indexLast = 0
//            if (isPrev) {
//                indexLast = index - 5
//                Log.i("test", "PREV _> indexLast $indexLast - index $index")
//                var subList = if (indexLast <= 0) {
//                    arrayList.subList(0, 5)
//                } else {
//                    arrayList.subList(indexLast, index)
//                }
            var subList = arrayList.subList(0, 5)
            Log.i("test","${subList.size}")
            managerTourInfoAdapter.tourInfoList = subList
            managerTourInfoAdapter.notifyDataSetChanged()
//                isNext = true
//            } else {
//                Toast.makeText(context, "Bạn đang ở trang đầu", Toast.LENGTH_LONG).show()
//            }
//            if (indexLast <= 0) {
//                isPrev = false
//                index = 6
//            } else {
//                index = indexLast
//            }

 */
*
*
*
*
* */