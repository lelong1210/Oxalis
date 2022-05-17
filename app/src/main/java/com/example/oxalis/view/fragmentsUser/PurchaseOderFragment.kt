package com.example.oxalis.view.fragmentsUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.oxalis.adapter.TabLayOutAdapter
import com.example.oxalis.databinding.FragmentPurchaseOderBinding
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.model.TourInfo
import com.example.oxalis.view.childrenPurchase.CancelBookTourFragment
import com.example.oxalis.view.childrenPurchase.ConfirmBookTourFragment
import com.example.oxalis.view.childrenPurchase.WaitingBookTourFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseOderFragment : Fragment() {

    private var _binding: FragmentPurchaseOderBinding? = null
    private val binding get() = _binding!!
    private val waitingBookTourFragment = WaitingBookTourFragment()
    private val confirmBookTourFragment = ConfirmBookTourFragment()
    private val cancelBookTourFragment = CancelBookTourFragment()
    var onItemClickFragment:((Fragment)->Unit)?=null
    var onItemMoreClick:((TourInfo)->Unit)?=null
    var onItemRating:((SheetAddInformationCart,TourInfo)->Unit)?=null
    private val tabTitle = arrayListOf("Đang chờ xác nhận", "Đã trãi nghiệm", "Đã Hủy")
    private val listTabFrameLayout = arrayListOf(waitingBookTourFragment,confirmBookTourFragment,cancelBookTourFragment)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPurchaseOderBinding.inflate(inflater, container, false)

        val viewPager2: ViewPager2 = binding.viewPagerPurchase
        val tabLayOut: TabLayout = binding.tabLayoutPurchase
        val tabLayOutAdapter = TabLayOutAdapter(activity?.supportFragmentManager!!, activity?.lifecycle!!,listTabFrameLayout)
        viewPager2.adapter = tabLayOutAdapter

        TabLayoutMediator(tabLayOut, viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        // waiting
        waitingBookTourFragment.onItemClickFragment={
            onItemClickFragment?.invoke(it)
        }
        waitingBookTourFragment.onItemMoreClick={
            onItemMoreClick?.invoke(it)
        }
        // confirm
        confirmBookTourFragment.onItemClickFragment={
            onItemClickFragment?.invoke(it)
        }
        confirmBookTourFragment.onItemMoreClick={
            onItemMoreClick?.invoke(it)
        }
        confirmBookTourFragment.onItemRating={sheetAddInformationCart, tourInfo ->
            onItemRating?.invoke(sheetAddInformationCart,tourInfo)
        }
        // cancel
        cancelBookTourFragment.onItemMoreClick={
            onItemMoreClick?.invoke(it)
        }
        cancelBookTourFragment.onItemClickFragment={
            onItemClickFragment?.invoke(it)
        }


        return binding.root
    }

}