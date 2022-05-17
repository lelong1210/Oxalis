package com.example.oxalis.view.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.oxalis.MainActivity
import com.example.oxalis.R
import com.example.oxalis.databinding.ActivityRegisterBinding
import com.example.oxalis.model.UserInfo
import com.example.oxalis.model.arrayStatusUserInfo
import com.example.oxalis.service.FirebaseService
import com.example.oxalis.view.login.LoginActivity
import com.google.android.gms.auth.account.WorkAccount.getClient
import com.google.android.gms.auth.api.AuthProxy.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    val firebaseService = FirebaseService()


    // constants
    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val fullname: String = binding.inputUserFullName.text.toString()
            val password: String = binding.inputPassword.text.toString()
            val repass:String = binding.inputRePassword.text.toString()
            val mail: String = binding.inputEmail.text.toString()

            if(fullname != "" && password !="" && repass != "" && mail != ""){
                if(password == repass){
                    var userInfo = UserInfo(
                        "",
                        fullname.uppercase(Locale.getDefault()),
                        mail,
                        "",
                        "",
                        "user",
                        "",
                        "",
                        "",
                        arrayStatusUserInfo[1]
                    )



                    firebaseService.createAccountAuth(userInfo, password) { status,userInfo ->
                        if (status) {
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(
                        this,
                        "Mật khẩu không khớp",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    this,
                    "các ô không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.gotoLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // config
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        // firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.googleLogin.setOnClickListener {
            singIn()
        }
    }
    private fun singIn(){
        val signIntent = googleSignInClient.signInIntent
        startActivityForResult(signIntent,RC_SIGN_IN)
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
                                insertSharedPreferences(userInfo)
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