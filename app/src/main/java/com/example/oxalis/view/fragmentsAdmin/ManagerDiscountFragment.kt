package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oxalis.adapter.DiscountItemAdapter
import com.example.oxalis.databinding.FragmentManagerDiscountBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailDiscountFragment

class ManagerDiscountFragment : Fragment() {

    private var _binding: FragmentManagerDiscountBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    private lateinit var managerDiscountRecyclerView: RecyclerView
    private lateinit var managerDiscountAdapter:DiscountItemAdapter
    var onItemClickManagerDiscountFragment: ((Fragment) -> Unit)? = null
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManagerDiscountBinding.inflate(inflater, container, false)

        firebaseService.getAllDiscount {
            setManagerDiscountListRecycler(it)
        }

        binding.addingBtn.setOnClickListener {
            var addDiscountFragment = AddDiscountFragment()
            onItemClickManagerDiscountFragment?.invoke(addDiscountFragment)
            addDiscountFragment.onClickRepeat={
                if(it){
                    onClickRepeat?.invoke(true)
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun setManagerDiscountListRecycler(listDiscount: List<Discount>) {
        managerDiscountRecyclerView = binding.listDiscountRecycler
        managerDiscountRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        managerDiscountAdapter = DiscountItemAdapter(requireContext(), listDiscount)
        managerDiscountRecyclerView.adapter = managerDiscountAdapter
        managerDiscountAdapter.onItemClickChild={
            var detailDiscountFragment = DetailDiscountFragment(it)
            onItemClickManagerDiscountFragment?.invoke(detailDiscountFragment)
            detailDiscountFragment.onClickRepeat={
                onClickRepeat?.invoke(true)
            }
        }
    }

}

/*binding.btnAddDiscount.setOnClickListener {

            val discount = Discount(
                "${binding.discountCode.text.toString()}",
                binding.percentDiscount.text.toString(),
                binding.numberAvailability.text.toString()
            )
            firebaseService.insertDiscount(discount) { status ->
                if (status) {
                    Toast.makeText(context, "Đã thêm mã giảm", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Thêm mã giảm giá thất bại", Toast.LENGTH_SHORT).show()
                }
            }


        }*/