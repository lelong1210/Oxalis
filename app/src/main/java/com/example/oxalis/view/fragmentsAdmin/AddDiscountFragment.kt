package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentAddDiscountBinding
import com.example.oxalis.databinding.FragmentDetailSheetBookTourBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.model.SheetAddInformationCart
import com.example.oxalis.service.FirebaseService

class AddDiscountFragment : Fragment() {

    private var _binding: FragmentAddDiscountBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDiscountBinding.inflate(inflater,container,false)

        binding.btnAddDiscount.setOnClickListener {

            val discount = Discount(
                "${binding.discountCode.text.toString()}",
                binding.percentDiscount.text.toString(),
                binding.numberAvailability.text.toString()
            )
            firebaseService.insertDiscount(discount) { status ->
                if (status) {
                    Toast.makeText(context, "Đã thêm mã giảm", Toast.LENGTH_SHORT).show()
                    onClickRepeat?.invoke(true)
                } else {
                    Toast.makeText(context, "Thêm mã giảm giá thất bại", Toast.LENGTH_SHORT).show()
                }
            }


        }


        // Inflate the layout for this fragment
        return binding.root
    }


}
