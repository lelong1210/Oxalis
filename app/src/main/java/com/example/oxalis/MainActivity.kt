package com.example.oxalis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oxalis.databinding.ActivityMainBinding
import com.example.oxalis.view.login.LoginActivity
import com.example.oxalis.view.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            finish()
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            finish()
        }
    }
}