package com.mobile.crud_berita.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.crud_berita.MainActivity
import com.mobile.crud_berita.R
import com.mobile.crud_berita.model.LoginResponse
import com.mobile.crud_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)

        val etUsername : EditText = findViewById(R.id.etUsername)
        val etPassword : EditText = findViewById(R.id.etPassword)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        val txtToRegister : TextView = findViewById(R.id.txtToRegister)

        //ketika teks belum punya akun diklik,maka akan ke page register
        txtToRegister.setOnClickListener(){
            val toRegister = Intent(this@LoginScreenActivity,
                RegisterScreenActivity::class.java)
            startActivity(toRegister)
        }

        btnLogin.setOnClickListener(){
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            //validasi input kosong
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    this@LoginScreenActivity, "Username atau Password Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ApiClient.retrofit.login(username,password).enqueue(
                    object  : Callback<LoginResponse>{

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful){
                                val loginResponse = response.body()
                                if (loginResponse != null &&  response.isSuccessful){
                                    //login berhasil
                                    Toast.makeText(
                                        this@LoginScreenActivity, "Login Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //mau pindah kepage lain
                                    val toMain = Intent(this@LoginScreenActivity, MainActivity::class.java)
                                    startActivity(toMain)
                                } else {
                                    //login gagal
                                    Toast.makeText(
                                        this@LoginScreenActivity, "Login Gagal",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                                Log.e("Login Error",errorMessage)
                                Toast.makeText(
                                    this@LoginScreenActivity, "Login Failure",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginScreenActivity, t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}