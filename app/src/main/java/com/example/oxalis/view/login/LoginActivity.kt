package com.example.oxalis.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityLoginBinding
import com.example.oxalis.service.FirebaseService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val mail = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            val firebaseService = FirebaseService()

            firebaseService.login(mail,password){
                userInfo ->
                if(userInfo.id != null){
                    Toast.makeText(this, "Login Succeed", Toast.LENGTH_SHORT).show()
                    Log.i("test","$userInfo")
                }else{
                    Toast.makeText(this, "Login Err", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }
}