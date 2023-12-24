package com.app.jastipapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.jastipapp.adapter.NoteAdapter
import com.app.jastipapp.models.Note
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CatatanActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var mUser: FirebaseUser
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navbar: BottomNavigationView
    private var listData: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatan)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!

        val userId = mUser.uid

        adapter = NoteAdapter(listData, this)
        rv = findViewById(R.id.rv)
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = adapter

        val query = FirebaseDatabase.getInstance().getReference("Note").child(userId)
        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                for (postData in snapshot.children){
                    val note = postData.getValue(Note::class.java)
                    if (note != null) {
                        listData.add(note)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        navbar = findViewById(R.id.navbar)
        navbar.setOnItemSelectedListener {
            val itemId = it.itemId
            if (itemId == R.id.home){
                startActivity( Intent(this, MainActivity::class.java))
                finish()
            } else if (itemId == R.id.profile){
                startActivity( Intent(this, ProfileActivity::class.java))
                finish()
            } else if (itemId != R.id.note){
               return@setOnItemSelectedListener true
            }
            false
        }
    }
}