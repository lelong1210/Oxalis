package com.example.oxalis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.oxalis.databinding.ActivityMainBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.view.fragments.*
import kotlin.reflect.full.memberProperties

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val preferentialFragment = PreferentialFragment()
    private val purchaseOderFragment = PurchaseOderFragment()
    private val searchFragment = SearchFragment()
    private val accountFragment = AccountFragment()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.HomeFragment->{
                    replaceFragment(homeFragment)
                    true
                }
                R.id.PreferentialFragment->{
                    replaceFragment(preferentialFragment)
                    true
                }
                R.id.PurchaseOderFragment->{
                    replaceFragment(purchaseOderFragment)
                    true
                }
                R.id.SearchFragment->{
                    replaceFragment(searchFragment)
                    true
                }
                R.id.AccountFragment->{
                    replaceFragment(accountFragment)
                    true
                }
                else -> false
            }
        }
        homeFragment.onItemClick = {
            it->replaceFragment(accountFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
}