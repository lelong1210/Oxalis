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
                fullname.uppercase(Locale.getDefault()),
                mail,
                "",
                "",
                "user",
                "https://firebasestorage.googleapis.com/v0/b/projectoxalis.appspot.com/o/avatar.png?alt=media&token=a8e069b4-34b4-43d7-834b-7d0a128f5f7a",
                "",
                ""
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