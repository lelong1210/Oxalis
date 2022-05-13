package com.example.oxalis.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityAdminBinding
import com.example.oxalis.view.fragmentsAdmin.HomeAdminFragment
import com.example.oxalis.view.fragmentsAdmin.ManagerBookTourFragment
import com.example.oxalis.view.login.LoginActivity

class AdminActivity : AppCompatActivity() {

    private val homeAdminFragment = HomeAdminFragment()
    private lateinit var binding: ActivityAdminBinding
    private var doubleBackToExitPressedOnce = false
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeAdminFragment)

        homeAdminFragment.onCardViewClick = {
            replaceFragment(it)
            index = 1
        }
        homeAdminFragment.backLogin={
            if (it){
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        if (index != -1) {
            replaceFragment(homeAdminFragment)
            index = -1
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}