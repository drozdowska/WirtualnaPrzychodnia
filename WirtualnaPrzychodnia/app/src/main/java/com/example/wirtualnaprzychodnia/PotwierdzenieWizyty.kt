package com.example.wirtualnaprzychodnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PotwierdzenieWizyty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potwierdzenie_wizyty)

        val godzina = intent.getStringExtra("godzina").toString()
        val data = intent.getStringExtra("data").toString()
        var textgodzina = findViewById<TextView>(R.id.godzina)
        var textdata = findViewById<TextView>(R.id.data)
        var textdane = findViewById<TextView>(R.id.dane)
        var numertel = findViewById<TextView>(R.id.numertel)
        var powrotmenu = findViewById<Button>(R.id.powrotmenu)
        textgodzina.setText(godzina)
        textdata.setText(data)
        var potwierdzenie = findViewById<Button>(R.id.potwierdzenie)
        var komunikat = findViewById<TextView>(R.id.komunikat)
        var komunikat2 = findViewById<TextView>(R.id.komunikat2)
        var auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().getReference("uzytkownicy")
        val uzytkownik= FirebaseAuth.getInstance().currentUser
        var uid=uzytkownik!!.uid

        powrotmenu.setOnClickListener {
            val start_menu = Intent(this, Menu::class.java)
            startActivity(start_menu)
        }
        database!!.child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val uzytkownik = dataSnapshot.getValue(Uzytkownik::class.java)
                textdane.setText(uzytkownik?.imie + " " + uzytkownik?.nazwisko)
                numertel.setText(uzytkownik?.telefon)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        var database2 = FirebaseDatabase.getInstance().getReference("zapisy")

        potwierdzenie.setOnClickListener {
            database2.child(data.toString()).child("wizyty").child(godzina).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val informacja = snapshot.getValue(Godziny::class.java)
                       // textdane.setText(informacja?.pacjent)
                        komunikat.setText("Przepraszamy. Podana godzina została właśnie zajęta. Wróć do poprzedniej strony")
                    } else {
                        database2!!.child(data.toString()).child("wizyty").child(godzina).child("pacjent").setValue(uid)
                        database2!!.child(data.toString()).child("wizyty").child(godzina).child("godzina").setValue(godzina)

                        komunikat.visibility = View.GONE
                        komunikat2.setText("Wizyta w dniu " + data + " o godzinie " + godzina + " została potwierdzona.")
                        powrotmenu.visibility=View.VISIBLE


                    }


                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
            }



    }
}