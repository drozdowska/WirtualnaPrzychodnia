package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext
import kotlin.coroutines.coroutineContext


class InformacjeAdapter(private val recycler: ArrayList<Nazwy>): RecyclerView.Adapter<InformacjeAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.obiekt, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val biezacy = recycler[position]
        holder.nazwa.text = biezacy.nazwa

        var inf_nazwa = biezacy.nazwa
        var inf_opis = biezacy.opis

        holder.itemView.setOnClickListener{v->

            val intent = Intent(v.context, InformacjeDetale::class.java)
            intent.putExtra("inf_nazwa",inf_nazwa)
            intent.putExtra("inf_opis",inf_opis)
            v.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return recycler.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nazwa : TextView = itemView.findViewById(R.id.nazwa)


    }


}