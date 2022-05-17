package com.example.oxalis.view.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.oxalis.R
import com.example.oxalis.admin.AdminActivity
import com.example.oxalis.databinding.ActivityDetailTourInfoBinding
import com.example.oxalis.model.TourInfo
import com.example.oxalis.model.UserInfo
import com.example.oxalis.view.fragmentsUser.AddInFormationBookTourFragment
import com.example.oxalis.view.login.LoginActivity
import com.google.gson.Gson

class DetailTourInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTourInfoBinding
    var btnBackClick: ((Boolean) -> Unit)? = null
    private lateinit var detailTourInfoFragment: DetailTourInfoFragment
    private lateinit var addInFormationBookTourFragment: AddInFormationBookTourFragment
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tourInfoJson = intent.getStringExtra("tourInfo")
        val tourInfo = gson.fromJson(tourInfoJson, TourInfo::class.java)
        detailTourInfoFragment = DetailTourInfoFragment(tourInfo)
        replaceFragment(detailTourInfoFragment)

        detailTourInfoFragment.btnBookTourClickChild = {
            val userInfo = getUserInfo()
            Log.i("test","--> $userInfo")
            if (userInfo.permission == null) {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            } else if(userInfo.permission == "admin"){
                val intent = Intent(this,AdminActivity::class.java)
                startActivity(intent)
            }else {
                addInFormationBookTourFragment = AddInFormationBookTourFragment(userInfo, tourInfo)
                replaceFragment(addInFormationBookTourFragment)
            }


        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun getUserInfo(): UserInfo {
        val gson = Gson()
        val pref = getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val json = pref?.getString("USERINFO", "NULL")

        return if (json == "NULL") {
            UserInfo(null,null,null,null,null,null)
        } else {
            gson.fromJson(json, UserInfo::class.java)
        }
    }
}