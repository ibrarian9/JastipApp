package com.app.jastipapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var login: TextView
    private lateinit var signUp: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        // Check if user is signed in and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //  Divine Id
        email = findViewById(R.id.edEmail)
        pass = findViewById(R.id.edPass)
        login = findViewById(R.id.submit)
        signUp = findViewById(R.id.signUp)
        mAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        login.setOnClickListener{

            val dataEmail = email.text.toString()
            val dataPass = pass.text.toString()

            if (TextUtils.isEmpty(dataEmail)){
                Toast.makeText(this, "Mohon Kolom Email di Isi...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dataPass)){
                Toast.makeText(this, "Mohon Kolom Password di Isi...", Toast.LENGTH_SHORT).show()
            } else {
               mAuth.signInWithEmailAndPassword(dataEmail, dataPass)
                   .addOnCompleteListener { t ->
                       if (t.isSuccessful) {
                           startActivity(Intent(this, MainActivity::class.java))
                           finish()
                       } else {
                           Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                       }
                   }
            }
        }
    }
}