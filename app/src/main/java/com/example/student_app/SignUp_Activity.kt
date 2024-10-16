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

class SignUp_Activity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSignup: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        btnSignup = findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val name = etName.text.toString()
            val age = etAge.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("name", name)
                editor.putString("age", age)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this,"plz fill in all fields",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
