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

        binding.selectImagebtn.setOnClickListener {
            selectImage()
        }

        binding.uploadimagebtn.setOnClickListener {
//            uploadImage()
//            getUrlImage()
        }

        return binding.root
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = ("image/*")
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            when (index) {
                0 -> {
                    binding.firebaseimage1.setImageURI(data?.data)
                    index++
                }
                1 -> {
                    binding.firebaseimage2.setImageURI(data?.data)
                    index++
                }
                2 -> {
                    binding.firebaseimage3.setImageURI(data?.data)
                    index++
                }
                3 -> {
                    binding.firebaseimage4.setImageURI(data?.data)
                    index++
                }
                else -> {
                    binding.firebaseimage4.setImageURI(data?.data)
                }
            }
            arrayImageUri.add(imageUri)
        }
    }

    private fun uploadImage() {

        Log.i("test", arrayImageUri.toString())

        var pd = ProgressDialog(context)
        pd.setTitle("upload")
        pd.show()

        val nameOfImage = arrayOf("1", "2", "3", "4", "5")


        for (i in 0 until arrayImageUri.size) {
            var imageRef =
                FirebaseStorage.getInstance().reference.child("image/${nameOfImage[i]}.jpg")
            imageRef.putFile(arrayImageUri[i])
                .addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(context, "file uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(context, "${p0.message}", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { p0 ->
                    var process: Double = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    pd.setMessage("Upload ${process.toInt()}%")

                }
        }


    }



    private fun insertStopPoint(){
//        val stopPointInfo = StopPointInfo()
    }
}