package com.example.oxalis.view.fragmentsAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.FragmentAddDiscountBinding
import com.example.oxalis.databinding.FragmentDetailDiscountBinding
import com.example.oxalis.model.Discount
import com.example.oxalis.service.FirebaseService

class DetailDiscountFragment(private val discount: Discount) : Fragment() {

    private var _binding: FragmentDetailDiscountBinding? = null
    private val binding get() = _binding!!
    private var firebaseService = FirebaseService()
    var onClickRepeat:((Boolean)->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDiscountBinding.inflate(inflater,container,false)

        binding.discountCode.setText(discount.id)
        binding.numberAvailability.setText(discount.numberAvailability)
        binding.percentDiscount.setText("${discount.percentDiscount}")


        binding.btnDelete.setOnClickListener {
            firebaseService.deleteDiscount(discount.id!!){
                if(it){
                    onClickRepeat?.invoke(true)
                    Toast.makeText(context, "Đã xóa mã giảm giá", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Xóa mã giảm giá thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnUpdate.setOnClickListener {

            discount.percentDiscount = binding.percentDiscount.text.toString()
            discount.numberAvailability = binding.numberAvailability.text.toString()

            firebaseService.insertDiscount(discount){
                if(it){
                    onClickRepeat?.invoke(true)
                    Toast.makeText(context, "Đã cập nhật mã giảm giá", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Cập nhật mã giảm giá thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}