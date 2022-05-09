package com.example.oxalis

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oxalis.admin.AdminActivity
import com.example.oxalis.databinding.ActivityMainBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.view.details.DetailTourInfoActivity
import com.example.oxalis.view.fragmentsAdmin.HomeAdminFragment
import com.example.oxalis.view.fragmentsAdmin.InsertStopPointFragment
import com.example.oxalis.view.fragmentsAdmin.InsertTourFragment
import com.example.oxalis.view.fragmentsUser.*
import com.example.oxalis.view.login.LoginActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val preferentialFragment = PreferentialFragment()
    private val purchaseOderFragment = PurchaseOderFragment()
    private val searchFragment = SearchFragment()
    private val accountFragment = AccountFragment()
    private val insertTourFragment = InsertTourFragment()
    private lateinit var json: String
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.HomeFragment -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.PreferentialFragment -> {
                    replaceFragment(preferentialFragment)
                    true
                }
                R.id.PurchaseOderFragment -> {
                    replaceFragment(purchaseOderFragment)
                    true
                }
                R.id.ChatFragment -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.AccountFragment -> {

                    val pref =
                        applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
                    json = pref.getString("USERINFO", "NULL").toString()
                    val gson = Gson()

                    if (json == "NULL") {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        val userInfo = gson.fromJson(json, UserInfo::class.java)
                        if (userInfo.permission.equals("user")) {
                            replaceFragment(accountFragment)
                        } else if (userInfo.permission.equals("admin")) {
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
                        }

                    }
                    true
                }
                else -> false
            }
        }
        homeFragment.onItemDiscountClick = { it ->

        }
        homeFragment.onItemTourInfoClick = {
            val intent = Intent(this, DetailTourInfoActivity::class.java)
            val gson = Gson()
            intent.putExtra("tourInfo", gson.toJson(it))
            startActivity(intent)
        }
        searchFragment.onItemClick = {
            replaceFragment(insertTourFragment)
        }
        preferentialFragment.onItemClick = {

        }
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}