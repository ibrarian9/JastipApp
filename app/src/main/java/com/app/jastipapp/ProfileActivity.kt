package com.app.jastipapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.app.jastipapp.models.UserDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class ProfileActivity : AppCompatActivity() {

    private lateinit var mUser: FirebaseUser
    private lateinit var mAuth: FirebaseAuth
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvEmail2: TextView
    private lateinit var logout: TextView
    private lateinit var navbar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

        tvNama = findViewById(R.id.tvNama)
        tvEmail = findViewById(R.id.tvEmail)
        tvEmail2 = findViewById(R.id.tvEmail2)
        logout = findViewById(R.id.logout)
        navbar = findViewById(R.id.navbar)

        val userId = mUser.uid

        val db = FirebaseDatabase.getInstance().getReference("Users")
        db.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val uDetail = snapshot.getValue<UserDetails>()
                if (uDetail != null){
                    val nama = uDetail.userName
                    val email = uDetail.userEmail
                    tvNama.text = nama
                    tvEmail.text = email
                    tvEmail2.text = email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        logout.setOnClickListener{
            mAuth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
            finish()
        }

        navbar.setOnItemSelectedListener {
            val itemId = it.itemId
            if (itemId == R.id.home){
                startActivity( Intent(this, MainActivity::class.java))
                finish()
            } else if (itemId == R.id.profile){
                return@setOnItemSelectedListener true
            } else if (itemId == R.id.note){
                startActivity( Intent(this, CatatanActivity::class.java))
                finish()
            }
            false
        }
    }
}