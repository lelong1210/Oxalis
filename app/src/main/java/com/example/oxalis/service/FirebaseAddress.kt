package com.example.oxalis.service

import android.util.Log
import com.example.oxalis.model.District
import com.example.oxalis.model.MessengerDetail
import com.example.oxalis.model.Province
import com.example.oxalis.model.Ward
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseAddress {
    private val database = Firebase.database
    private var address = database.getReference("address")


    fun getAddress(callback: (listProvince: List<Province>) -> Unit) {

        val address = database.getReference("address")
        val listProvince = ArrayList<Province>()
        address.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (i in 0 until snapshot.childrenCount){
//                    Log.i("test","id $i --${snapshot.child("$i").child("name").value}")
                    val nameProvince = snapshot.child("$i").child("name").value
                    val districts = snapshot.child("$i").child("districts")
                    val listDistrict = ArrayList<District>()
                    for(indexDistrict in 0 until districts.childrenCount){
                        val district = districts.child("$indexDistrict")
//                        Log.i("test","------------>district : ${district.child("name").value}")
                        val nameDistrict = district.child("name").value
                        val wards = districts.child("$indexDistrict").child("wards")
                        val listWard = ArrayList<Ward>()
                        for(indexWard in 0 until wards.childrenCount){
                            val ward = wards.child("$indexWard")
//                            Log.i("test","------------------------->ward : ${ward.child("name").value}")
                            val nameWard = ward.child("name").value
                            val wardClass = Ward("$indexWard","$nameWard")
                            listWard.add(wardClass)
                        }
                        val districtClass = District("$indexDistrict","$nameDistrict",listWard)
                        listDistrict.add(districtClass)
                    }
                    val provinceClass = Province("$i","$nameProvince",listDistrict)
                    listProvince.add(provinceClass)
                }
                callback?.invoke(listProvince)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("test", "Failed to read value.", error.toException())
            }
        })
    }

    fun getProvinces() {

    }

    fun getDistricts() {

    }

    fun getWards() {

    }
}
//                val arrayList = ArrayList<MessengerDetail>()
//                for (postSnapshot in snapshot.children) {
//                    val messengerDetail = postSnapshot.getValue(MessengerDetail::class.java)
//                    arrayList.add(messengerDetail!!)
//                }
//                callback.invoke(arrayList)