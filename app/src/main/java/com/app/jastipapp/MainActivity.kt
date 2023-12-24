package com.app.jastipapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.jastipapp.adapter.MainAdapter
import com.app.jastipapp.models.DataJastip
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var search: SearchView
    private lateinit var rv: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var navbar: BottomNavigationView
    private lateinit var pb: ProgressBar
    var listData: MutableList<DataJastip> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.searchView)
        pb = findViewById(R.id.pb)
        mainAdapter = MainAdapter(listData, this)
        rv = findViewById(R.id.rv)
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = mainAdapter

        pb.visibility = View.VISIBLE

        val query = FirebaseDatabase.getInstance().getReference("Data")
        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                mainAdapter.clear()
               for (postData in snapshot.children){
                   val dataJastip = postData.getValue(DataJastip::class.java)
                   if (dataJastip != null) {
                       listData.add(dataJastip)
                       pb.visibility = View.GONE
                   }
                   mainAdapter.notifyDataSetChanged()
                   pb.visibility = View.GONE
               }
            }
            override fun onCancelled(error: DatabaseError) {
                pb.visibility = View.GONE
            }
        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        navbar = findViewById(R.id.navbar)
        navbar.setOnItemSelectedListener {
            val itemId = it.itemId
            if (itemId == R.id.home){
                return@setOnItemSelectedListener true
            } else if (itemId == R.id.profile){
                startActivity( Intent(this, ProfileActivity::class.java))
                finish()
            } else if (itemId == R.id.note){
                startActivity( Intent(this, CatatanActivity::class.java))
                finish()
            }
            false
        }
    }

    private fun filterList(newText: String?) {
        val filterData: ArrayList<DataJastip> = ArrayList()
        if (newText != null && newText != ""){
            for (i in listData){
                if (i.nama?.lowercase(Locale.ROOT)?.contains(newText) == true){
                    filterData.add(i)
                }
            }

            if (filterData.isEmpty()){
                rv.visibility = View.GONE
            } else {
                rv.visibility = View.VISIBLE
                mainAdapter.filterList(filterData)
            }
        } else {
            rv.visibility = View.VISIBLE
            mainAdapter.setData(listData)
        }
    }
}