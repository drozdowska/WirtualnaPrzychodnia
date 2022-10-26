package com.example.wirtualnaprzychodnia

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class TematyAdapter( private val recycler: ArrayList<Temat>): RecyclerView.Adapter<TematyAdapter.RecyclerTematy>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTematy {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.temat_forum, parent, false)
        return RecyclerTematy(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerTematy, position: Int) {
        val biezacy = recycler[position]

        holder.temat.text = biezacy.tytul


        var for_temat = biezacy.tytul
        var id = biezacy.id
        var autor = biezacy.autor
        var uzytkownicy = FirebaseDatabase.getInstance().getReference("uzytkownicy")
        if (autor != null) {
            uzytkownicy!!.child(autor).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val uzytkownik = dataSnapshot.getValue(Uzytkownik::class.java)
                    holder.autor.setText(uzytkownik?.nick)


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }




        holder.itemView.setOnClickListener{v->

            val intent = Intent(v.context, TematDetale::class.java)
            intent.putExtra("id", id)
            v.context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return recycler.size
    }

    class RecyclerTematy(itemView: View) : RecyclerView.ViewHolder(itemView){
        val autor : TextView = itemView.findViewById(R.id.autor)
        val temat : TextView = itemView.findViewById(R.id.temat)

    }
}