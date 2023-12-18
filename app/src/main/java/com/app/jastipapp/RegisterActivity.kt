package com.app.jastipapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.jastipapp.models.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var nama: EditText
    private lateinit var pass: EditText
    private lateinit var submit: TextView
    private lateinit var signIn: TextView
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
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email)
        nama = findViewById(R.id.nama)
        pass = findViewById(R.id.pass)
        submit = findViewById(R.id.submit)
        signIn = findViewById(R.id.signIn)
        mAuth = FirebaseAuth.getInstance()

        signIn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        submit.setOnClickListener{
            val dataNama = nama.text.toString()
            val dataEmail = email.text.toString()
            val dataPass = pass.text.toString()

            if (TextUtils.isEmpty(dataEmail)){
                Toast.makeText(this, "Mohon untuk mengisi kolom Email...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dataPass)){
                Toast.makeText(this, "Mohon untuk mengisi kolom Password...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dataNama)) {
                Toast.makeText(this, "Mohon untuk mengisi kolom Nama...", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.createUserWithEmailAndPassword(dataEmail, dataPass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){

                            val fUser = mAuth.currentUser
                            val uid = fUser!!.uid
                            val userDetail = UserDetails(dataEmail, dataNama)

                            val db = FirebaseDatabase.getInstance().getReference("Users")
                            db.child(uid).setValue(userDetail).addOnCompleteListener{
                                if (task.isSuccessful){
                                    val i = Intent(this, MainActivity::class.java)
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(i)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Account Registered Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Account Registered Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}