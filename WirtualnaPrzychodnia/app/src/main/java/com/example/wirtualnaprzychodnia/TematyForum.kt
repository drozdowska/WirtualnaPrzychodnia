package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TematyForum : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Temat>
    private lateinit var database: FirebaseFirestore
    private lateinit var fraza: EditText
   // lateinit var fraza: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tematy_forum)

        recyclerView = findViewById(R.id.recyclerforum)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<Temat>()
        database = FirebaseFirestore.getInstance()
        fraza = findViewById<EditText>(R.id.fraza)
        var szukaj = findViewById<Button>(R.id.szukaj)
        pobieraniedanych()
        var linearwyszukiwanie = findViewById<LinearLayout>(R.id.linearwyszukiwanie)
        var wyszukiwanie = findViewById<Button>(R.id.wyszukiwanie)
        wyszukiwanie.setOnClickListener {
            if (linearwyszukiwanie.visibility == View.VISIBLE) {
                linearwyszukiwanie.visibility = View.GONE
                wyszukiwanie.text = "wyszukiwanie"
                pobieraniedanych()
            }
            else {
                linearwyszukiwanie.visibility = View.VISIBLE
                szukaj.setOnClickListener {
                    if (fraza.text.isNotEmpty()){
                    pobieraniewyszukiwanie()
                    }
                }
                wyszukiwanie.text = "ukryj wyszukiwanie"
            }
        }



        var dodawanie_tematu = findViewById<Button>(R.id.dodawanie_tematu)
        dodawanie_tematu.setOnClickListener{
            val start_dodawanie_tematu = Intent(this, ForumDodawanieTematu::class.java)
            startActivity(start_dodawanie_tematu)
        }
    }






    private fun pobieraniedanych() {
        var id:String = "xxxx"
        arrayList.clear()
        recyclerView.adapter?.notifyDataSetChanged()
        database.collection("forum").orderBy("data", Query.Direction.DESCENDING).get().addOnSuccessListener{ result ->
                        //Toast.makeText(applicationContext, "ok", Toast.LENGTH_SHORT ).show()

                    for (document in result) {
                        val informacja = document.toObject(Temat::class.java)
                        informacja.id = document.id

                        arrayList.add(informacja)

                        }
                    recyclerView.adapter = TematyAdapter(arrayList)

                    }
            .addOnFailureListener{
                Toast.makeText(applicationContext, "blad", Toast.LENGTH_SHORT ).show()
            }
        }

    private fun pobieraniewyszukiwanie() {
        var id:String = "xxxx"
        arrayList.clear()
        recyclerView.adapter?.notifyDataSetChanged()
        var search = fraza.text.toString()

        var ref = database.collection("forum").orderBy("tytul")
        ref.startAt(search).endAt( search + "\uf8ff").get().addOnSuccessListener{ result ->
            //Toast.makeText(applicationContext, "ok", Toast.LENGTH_SHORT ).show()
            for (document in result) {
                val informacja = document.toObject(Temat::class.java)
                informacja.id = document.id

                arrayList.add(informacja)

            }
            recyclerView.adapter = TematyAdapter(arrayList)

        }
            .addOnFailureListener{
                Toast.makeText(applicationContext, "blad", Toast.LENGTH_SHORT ).show()
            }
    }
    }





