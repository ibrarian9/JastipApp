package com.app.jastipapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.jastipapp.DetailActivity
import com.app.jastipapp.R
import com.app.jastipapp.models.DataJastip
import com.squareup.picasso.Picasso

class MainAdapter(private var data: MutableList<DataJastip>, context: Context): RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = mInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        val listData = data[pos]

        Picasso.get().load(listData.poto).fit().into(holder.poto)
        holder.tvNama.text = listData.nama
        holder.tvHarga.text = "Rp" + listData.harga

        holder.itemView.setOnClickListener{ v ->
            val i = Intent(v.context, DetailActivity::class.java)
            i.putExtra("nama", listData.nama)
            i.putExtra("harga", listData.harga)
            i.putExtra("poto", listData.poto)
            v.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataa: List<DataJastip>) {
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
    fun filterList(list: MutableList<DataJastip>) {
        this.data = list
        notifyDataSetChanged()
    }

    class MyViewHolder(v :View): RecyclerView.ViewHolder(v) {
        val poto: ImageView = v.findViewById(R.id.poto)
        val tvNama: TextView = v.findViewById(R.id.nama)
        val tvHarga: TextView = v.findViewById(R.id.harga)
    }
}