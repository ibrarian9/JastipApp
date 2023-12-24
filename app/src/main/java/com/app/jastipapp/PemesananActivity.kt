package com.app.jastipapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.jastipapp.models.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class PemesananActivity : AppCompatActivity() {

    private lateinit var simpan : TextView
    private lateinit var poto : ImageView
    private lateinit var nama : EditText
    private lateinit var barang : EditText
    private lateinit var harga : EditText
    private lateinit var jumlah : EditText
    private lateinit var jastiper : EditText
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mUser : FirebaseUser
    private var maxId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!
        val uid = mUser.uid

        poto = findViewById(R.id.ivPoto)
        val dataPoto = intent.getStringExtra("poto")
        Picasso.get().load(dataPoto).fit().into(poto)

        nama = findViewById(R.id.namaLengkap)
        barang = findViewById(R.id.namaBarang)
        harga = findViewById(R.id.harga)
        jumlah = findViewById(R.id.jumlah)
        jastiper = findViewById(R.id.jastiper)

        //  get Data
        val dataNama = intent.getStringExtra("nama")
        val dataHarga = intent.getStringExtra("harga")
        barang.setText(dataNama)
        harga.setText(dataHarga)

        val red = FirebaseDatabase.getInstance().getReference("Note").child(uid)
        red.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    maxId = (snapshot.childrenCount)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        simpan = findViewById(R.id.simpan)
        simpan.setOnClickListener{
            val dNama = nama.text.toString()
            val dBarang = barang.text.toString()
            val dHarga = harga.text.toString()
            val dJumlah = jumlah.text.toString()
            val dJastip = jastiper.text.toString()

            if (TextUtils.isEmpty(dNama)){
                Toast.makeText(this, "Nama masih kosong...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dBarang)){
                Toast.makeText(this, "Nama Barang masih kosong...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dHarga)){
                Toast.makeText(this, "Harga masih kosong...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dJumlah)){
                Toast.makeText(this, "Jumlah masih kosong...", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(dJastip)){
                Toast.makeText(this, "Jastiper masih kosong...", Toast.LENGTH_SHORT).show()
            } else {

                val dataNote = Note(dNama, dBarang, dHarga, dJumlah, dJastip, dataPoto)

                val ref = FirebaseDatabase.getInstance().getReference("Note").child(uid)
                ref.child((maxId + 1).toString()).setValue(dataNote).addOnCompleteListener{ t ->
                    if (t.isSuccessful){
                        Toast.makeText(this, "Data Pemesanan berhasil ditambah...", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Data Pemesanan Gagal ditambah...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}