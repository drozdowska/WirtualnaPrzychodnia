package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class GodzinyAdapter(private val recycler: ArrayList<Godziny>) : RecyclerView.Adapter<ZapisyAdapter.RecyclerViewHolder>() {
    private lateinit var database2: DatabaseReference
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZapisyAdapter.RecyclerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.obiekt_zapisy, parent, false)
        return ZapisyAdapter.RecyclerViewHolder(itemView)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nazwa : TextView = itemView.findViewById(R.id.nazwa)
        val stop : TextView = itemView.findViewById(R.id.godzinyprzyjec)
    }

    override fun getItemCount(): Int {
        return recycler.size
    }

    override fun onBindViewHolder(holder: ZapisyAdapter.RecyclerViewHolder, position: Int) {
        val biezacy = recycler[position]
        holder.nazwa.text = biezacy.godzina
        //holder.stop.text = biezacy.stop



        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, PotwierdzenieWizyty::class.java)
            intent.putExtra("godzina",biezacy.godzina)
            intent.putExtra("data",biezacy.data)
            v.context.startActivity(intent)


        }

    }

}