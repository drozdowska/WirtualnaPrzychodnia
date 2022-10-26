package com.example.wirtualnaprzychodnia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Informacje : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Nazwy>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacje)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<Nazwy>()
        pobieraniedanych()





    }
    private fun pobieraniedanych() {
    database = FirebaseDatabase.getInstance().getReference("informacje")



    database.addListenerForSingleValueEvent(object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {

            if (snapshot.exists()) {

                for (snapshot in snapshot.children) {
                    val informacja = snapshot.getValue(Nazwy::class.java)

                        arrayList.add(informacja!!)

                }
                recyclerView.adapter = InformacjeAdapter(arrayList)

            }

        }

        override fun onCancelled(error: DatabaseError) {

        }

    })
}}