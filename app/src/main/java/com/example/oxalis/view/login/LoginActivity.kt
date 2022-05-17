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
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val firebaseService = FirebaseService()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    // constants
    companion object{
        private const val RC_SIGN_IN = 140
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // config
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        // firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener {
            val mail = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if(mail != "" && password != ""){
                firebaseService.login(mail, password) { userInfo,status ->
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
            }else{
                Toast.makeText(this, "Các ô không được để trống", Toast.LENGTH_SHORT).show()
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
            finish()
        }
        binding.googleLogin.setOnClickListener {
            singIn()
        }
    }
    private fun singIn(){
        val signIntent = googleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.i("test","-+-+-+-+> $e")
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) {
                task->
            if(task.isSuccessful){
                val user = firebaseAuth.currentUser
                firebaseService.checkUserExit{userInfoST,statusST->
                    if(statusST){
                        insertSharedPreferences(userInfoST)
                    }else{
                        var userInfo = UserInfo(
                            "${user?.uid}",
                            user?.displayName,
                            "${user?.email}",
                            "",
                            "",
                            "user",
                            "${user?.uid}avatar",
                            "",
                            "",
                            arrayStatusUserInfo[1]
                        )
                        firebaseService.insertAccountGoogle(userInfo){status->
                            if(status){
                                Toast.makeText(this,"Đăng Ký Thành Công",Toast.LENGTH_SHORT).show()
                                insertSharedPreferences(userInfoST)
                            }else{
                                Toast.makeText(this,"Đăng Ký Thất Bại",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }


                firebaseAuth.signOut()
                googleSignInClient.signOut()
            }else{
                Log.i("test","======================> err : ${firebaseAuth.currentUser}")
            }
        }.addOnFailureListener {

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