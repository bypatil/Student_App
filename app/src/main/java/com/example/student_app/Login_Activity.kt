package com.example.student_app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login_Activity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        //check if the user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn",false)){
            navigateToHome()
        }

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val storedUsername = sharedPreferences.getString("username",null)
            val storedPassword = sharedPreferences.getString("password",null)

            if (username == storedUsername && password == storedPassword){
                sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
                navigateToHome()
            }else{
                Toast.makeText(this,"Invalid credentials",Toast.LENGTH_SHORT).show()
            }
        }
        btnSignup.setOnClickListener {
            val intent = Intent(this,SignUp_Activity::class.java)
            startActivity(intent)
        }



    }

    private fun navigateToHome(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}