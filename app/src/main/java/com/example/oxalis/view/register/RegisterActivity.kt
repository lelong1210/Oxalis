package com.example.oxalis.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityRegisterBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.service.FirebaseService
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val fullname: String = binding.inputUserFullName.text.toString()
            val password: String = binding.inputPassword.text.toString()
            val mail: String = binding.inputEmail.text.toString()

            var userInfo = UserInfo(
                "",
                fullname.uppercase(Locale.getDefault()), mail, "", "", "user", "", "", ""
            )
            val firebaseService = FirebaseService()


            firebaseService.createAccountAuth(userInfo, password) { status ->
                if (status) {
                    Toast.makeText(this, "register succeed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "register error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}