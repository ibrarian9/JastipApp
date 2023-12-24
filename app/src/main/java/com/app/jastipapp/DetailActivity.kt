package com.app.jastipapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var poto: ImageView
    private lateinit var nama: TextView
    private lateinit var harga: TextView
    private lateinit var order: TextView
    private lateinit var ig: TextView
    private lateinit var wa: TextView
    private lateinit var navbar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataIg = intent.getStringExtra("ig")
        val dataWa = "https://wa.me/6282285062470"
        wa = findViewById(R.id.wa)
        wa.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(dataWa))
            startActivity(i)
        }

        ig = findViewById(R.id.ig)
        ig.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(dataIg))
            startActivity(i)
        }

        val dataNama = intent.getStringExtra("nama")
        nama = findViewById(R.id.tvNama)
        nama.text = dataNama

        val dataHarga = intent.getStringExtra("harga")
        harga = findViewById(R.id.tvHarga)
        harga.text = dataHarga

        val dataPoto = intent.getStringExtra("poto")
        poto = findViewById(R.id.ivPoto)
        Picasso.get().load(dataPoto).fit().into(poto)

        order = findViewById(R.id.order)
        order.setOnClickListener{
            val i = Intent(this, PemesananActivity::class.java)
            i.putExtra("nama", dataNama)
            i.putExtra("harga", dataHarga)
            i.putExtra("poto", dataPoto)
            i.putExtra("ig", dataIg)
            startActivity(i)
        }

        navbar = findViewById(R.id.navbar)
        navbar.setOnItemSelectedListener {
            val itemId = it.itemId
            if (itemId == R.id.home){
                startActivity( Intent(this, MainActivity::class.java))
                finish()
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
}