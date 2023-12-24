package com.app.jastipapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.jastipapp.R
import com.app.jastipapp.models.DataJastip
import com.app.jastipapp.models.Note
import com.squareup.picasso.Picasso

class NoteAdapter(private var data: MutableList<Note>, context: Context): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = mInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        val list = data[pos]

        Picasso.get().load(list.foto).fit().into(holder.poto)
        holder.tvNama.text = list.nama
        holder.tvBarang.text = list.barang
        holder.tvHarga.text = "Rp." + list.harga
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataa: List<Note>) {
        data.apply {
            clear()
            addAll(dataa)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(listt: MutableList<Note>) {
        this.data = listt
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val poto: ImageView = v.findViewById(R.id.poto)
        val tvNama: TextView = v.findViewById(R.id.nama)
        val tvBarang: TextView = v.findViewById(R.id.barang)
        val tvHarga: TextView = v.findViewById(R.id.harga)
    }

}