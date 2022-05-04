package com.example.oxalis.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityLoginBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.register.RegisterActivity
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val firebaseService = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val mail = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            firebaseService.login(mail, password) { userInfo ->
                if (userInfo.id != null) {
                    insertSharedPreferences(userInfo)
                    Toast.makeText(this, "Login Succeed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                } else {
                    Toast.makeText(this, "Login Err", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.forgotPassword.setOnClickListener {
            val email = binding.inputEmail.text.toString()

            if (email == "") {
                Toast.makeText(
                    this,
                    "Quý khách hãy nhập mail để thực hiện chức năng",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                firebaseService.sendPasswordResetEmail(email) { status ->

                    if (status) {
                        Toast.makeText(this, "Mail đã gửi về mail của Quý khách", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        binding.gotoRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertSharedPreferences(userInfo: UserInfo) {
        val pref = applicationContext.getSharedPreferences("PrefName", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val gson = Gson()
        var json = gson.toJson(userInfo)
        editor.putString("USERINFO", json)
        editor.commit()
    }
}