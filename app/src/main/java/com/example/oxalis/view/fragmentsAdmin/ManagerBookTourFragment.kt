package com.example.oxalis.view.fragmentsAdmin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.oxalis.adapter.BookTourAdapter
import com.example.oxalis.adapter.TabLayOutAdapter
import com.example.oxalis.databinding.FragmentManagerBookTourBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.arrayOfStatusSheet
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.childrenBookSheetTourAdmin.WaitingBookTourFragment
import com.example.oxalis.view.childrenBookSheetTourAdmin.CancelBookTourFragment
import com.example.oxalis.view.childrenBookSheetTourAdmin.ConfirmBookTourFragment
import com.example.oxalis.view.details.DetailSheetBookTourAdminConfirmFragment
import com.example.oxalis.view.details.DetailSheetBookTourFragment
import com.example.oxalis.view.details.DetailSheetBookTourUserWaitingFragment
import com.example.oxalis.view.details.DetailTourInfoActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class ManagerBookTourFragment : Fragment() {

    private var _binding: FragmentManagerBookTourBinding? = null
    private val binding get() = _binding!!
    private val waitingBookTourFragment = WaitingBookTourFragment()
    private val confirmBookTourFragment = ConfirmBookTourFragment()
    private val cancelBookTourFragment = CancelBookTourFragment()
    private val firebaseService = FirebaseService()
    var onClickItemManagerBookTourFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat: ((Boolean) -> Unit)? = null
    var onItemMoreClick: ((TourInfo) -> Unit)? = null
    private val tabTitle = arrayListOf("Đang chờ xác nhận", "Đã xác nhận", "Đã Hủy")
    private val listTabFrameLayout =
        arrayListOf(waitingBookTourFragment, confirmBookTourFragment, cancelBookTourFragment)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManagerBookTourBinding.inflate(inflater, container, false)

        val viewPager2: ViewPager2 = binding.viewPagerPurchase
        val tabLayOut: TabLayout = binding.tabLayoutPurchase
        val tabLayOutAdapter = TabLayOutAdapter(
            activity?.supportFragmentManager!!,
            activity?.lifecycle!!,
            listTabFrameLayout
        )
        viewPager2.adapter = tabLayOutAdapter

        TabLayoutMediator(tabLayOut, viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        // waiting confirm
        waitingBookTourFragment.onClickItem = { sheetAddInformationCart ->
            val detailSheetBookTourFragment = DetailSheetBookTourFragment(sheetAddInformationCart)
            onClickItemManagerBookTourFragment?.invoke(detailSheetBookTourFragment)

            detailSheetBookTourFragment.onClickRepeat = {
                onClickRepeat?.invoke(it)
            }
        }
        // confirm
        confirmBookTourFragment.onClickItem = { sheetAddInformationCart ->
            firebaseService.getTourWhereId(sheetAddInformationCart.idTour.toString()) { tourInfo ->
                val detailSheetBookTourAdminConfirmFragment =
                    DetailSheetBookTourAdminConfirmFragment(sheetAddInformationCart, tourInfo)
                onClickItemManagerBookTourFragment?.invoke(detailSheetBookTourAdminConfirmFragment)

                detailSheetBookTourAdminConfirmFragment.onItemMoreClick = { tourInfo ->
                    onItemMoreClick?.invoke(tourInfo)
                }
            }
        }
        // cancel
        cancelBookTourFragment.onClickItem = { sheetAddInformationCart ->
            firebaseService.getTourWhereId(sheetAddInformationCart.idTour.toString()) { tourInfo ->
                val detailSheetBookTourAdminConfirmFragment =
                    DetailSheetBookTourAdminConfirmFragment(sheetAddInformationCart, tourInfo)
                onClickItemManagerBookTourFragment?.invoke(detailSheetBookTourAdminConfirmFragment)

                detailSheetBookTourAdminConfirmFragment.onItemMoreClick = { tourInfo ->
                    onItemMoreClick?.invoke(tourInfo)
                }
            }
        }


        return binding.root
    }

//    firebaseService.getAllSheetBookTour {
//        setManagerBookTourListRecycler(it)
//    }
//    private fun setManagerBookTourListRecycler(listBookTour: List<SheetAddInformationCart>) {
//        managerBookTourRecyclerView = binding.listBookTourRecycler
//        managerBookTourRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        managerBookTourAdapter = BookTourAdapter(requireContext(), listBookTour)
//        managerBookTourRecyclerView.adapter = managerBookTourAdapter
//        managerBookTourAdapter.onClickItem = { sheetAddInformationCart ->
//            val detailSheetBookTourFragment = DetailSheetBookTourFragment(sheetAddInformationCart)
//            onClickItemManagerBookTourFragment?.invoke(detailSheetBookTourFragment)
//
//            detailSheetBookTourFragment.onClickRepeat={
//                onClickRepeat?.invoke(it)
//            }
//        }
//    }

}