package com.example.oxalis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.oxalis.databinding.FragmentDetailDiscountUserBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.rating.RatingFragment
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.details.DetailDiscountUserFragment
import com.example.oxalis.view.details.DetailTourInfoActivity
import com.example.oxalis.view.fragmentsAdmin.AddUserFragment
import com.example.oxalis.view.fragmentsAdmin.InsertTourFragment
import com.example.oxalis.view.fragmentsUser.*
import com.example.oxalis.view.login.LoginActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val preferentialFragment = PreferentialFragment()
    private val purchaseOderFragment = PurchaseOderFragment()
    private val chatFragment = ChatFragment()
    private val accountFragment = AccountFragment()
    private val insertTourFragment = InsertTourFragment()
    private lateinit var resultSearchFragment: ResultSearchFragment
    private lateinit var detailDiscountUserFragment: DetailDiscountUserFragment
    private lateinit var updateAccountFragment: UpdateAccountFragment
    private lateinit var json: String
    private val gson = Gson()
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private val firebaseService = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        updateInformation()
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
                    val pref =
                        applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
                    json = pref.getString("USERINFO", "NULL").toString()
                    if (json == "NULL") {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        val userInfo = gson.fromJson(json, UserInfo::class.java)
                        if (userInfo.permission.equals("user")) {
                            replaceFragment(purchaseOderFragment)
                        } else if (userInfo.permission.equals("admin")) {
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    true
                }
                R.id.ChatFragment -> {
                    val pref =
                        applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
                    json = pref.getString("USERINFO", "NULL").toString()
                    if (json == "NULL") {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        val userInfo = gson.fromJson(json, UserInfo::class.java)

                        Log.i("test","$userInfo")

                        if (userInfo.permission.equals("user")) {
                            replaceFragment(chatFragment)
                        } else if (userInfo.permission.equals("admin")) {
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    true
                }
                R.id.AccountFragment -> {

                    val pref =
                        applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
                    json = pref.getString("USERINFO", "NULL").toString()

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
        // home
        homeFragment.onItemDiscountClick = { it ->

        }
        homeFragment.onItemSearchClick = {
            resultSearchFragment = ResultSearchFragment(it)
            replaceFragment(resultSearchFragment)


            resultSearchFragment.onItemClick = { tourInfo ->
                val intent = Intent(this, DetailTourInfoActivity::class.java)
                val gson = Gson()
                intent.putExtra("tourInfo", gson.toJson(tourInfo))
                startActivity(intent)
            }

        }
        homeFragment.onItemTourInfoClick = {
            val intent = Intent(this, DetailTourInfoActivity::class.java)
            val gson = Gson()
            intent.putExtra("tourInfo", gson.toJson(it))
            startActivity(intent)
        }
        // chat
        chatFragment.onItemClick = {
            replaceFragment(insertTourFragment)
        }
        // pre
        preferentialFragment.onItemClick = {
            val pref =
                applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
            json = pref.getString("USERINFO", "NULL").toString()

            if (json == "NULL") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                detailDiscountUserFragment = DetailDiscountUserFragment(it)
                replaceFragment(detailDiscountUserFragment)
            }
        }
        // account
        accountFragment.onBtnEdit = { userInfo ->
            updateAccountFragment = UpdateAccountFragment(userInfo)
            replaceFragment(updateAccountFragment)

            updateAccountFragment.onClickItemAccountFragment={fragment->
                replaceFragment(fragment)
            }
            updateAccountFragment.backLogin={
                if(logOut()){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        // purchase
        purchaseOderFragment.onItemClickFragment = {
            replaceFragment(it)
        }
        purchaseOderFragment.onItemMoreClick = { tourInfo ->
            val intent = Intent(this, DetailTourInfoActivity::class.java)
            val gson = Gson()
            intent.putExtra("tourInfo", gson.toJson(tourInfo))
            startActivity(intent)
        }
        purchaseOderFragment.onItemRating = { sheetAddInformationCart, tourInfo ->
            val ratingFragment = RatingFragment(sheetAddInformationCart, tourInfo)
            replaceFragment(ratingFragment)
            ratingFragment.onItemRate = {
                if (it) {
                    replaceFragment(purchaseOderFragment)
                }
            }
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
    fun logOut(): Boolean {
        val pref = getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.remove("USERINFO")
        return editor?.commit()!!
    }
    private fun updateInformation(){
        val pref =
            applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        json = pref.getString("USERINFO", "NULL").toString()
        if (json == "NULL") {

        } else {
            var userInfo = gson.fromJson(json, UserInfo::class.java)
            firebaseService.updateInformationUser(userInfo.id.toString()) { userInfoTmp ->
                insertSharedPreferences(userInfoTmp)
                userInfo = userInfoTmp

                if(userInfoTmp.status == arrayStatusUserInfo[0]){
                    logOut()
                    val mAlertDialog = AlertDialog.Builder(this)
                    mAlertDialog.setIcon(R.drawable.logo9) //set alertdialog icon
                    mAlertDialog.setTitle("Thông Báo !!!") //set alertdialog title
                    mAlertDialog.setMessage("Tài khoản của bạn đã bị khóa") //set alertdialog message
                    mAlertDialog.setPositiveButton("OK") { dialog, id ->
                        //perform some tasks here
//                        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()
                    }
//                    mAlertDialog.setNegativeButton("THOÁT") { dialog, id ->
//                        //perform som tasks here
//                        Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
//                    }
                    mAlertDialog.show()
                }else{

                }



            }
        }
    }
    private fun insertSharedPreferences(userInfo: UserInfo):Boolean {
        val pref = applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val gson = Gson()
        var json = gson.toJson(userInfo)
        editor.putString("USERINFO", json)
        return editor.commit()
    }

}